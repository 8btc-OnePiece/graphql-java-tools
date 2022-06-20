package graphql.kickstart.tools

import graphql.language.FieldDefinition
import graphql.language.InputValueDefinition
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment

/**
 * @author Andrew Potter
 */
internal abstract class FieldResolver(val field: FieldDefinition, val search: FieldResolverScanner.Search, val options: SchemaParserOptions, relativeTo: JavaType) {
    val resolverInfo: ResolverInfo = search.resolverInfo
    val genericType = GenericType(search.type, options).relativeToPotentialParent(relativeTo)

    abstract fun scanForMatches(): List<TypeClassMatcher.PotentialMatch>
    abstract fun createDataFetcher(): DataFetcher<*>

    /**
     * Add source resolver depending on whether or not this is a resolver method
     */
    protected fun getSourceResolver(): SourceResolver = if(this.search.source != null) {
        ({ this.search.source })
    } else {
        ({ environment ->
            val source = environment.getSource<Any>()

            if(!this.genericType.isAssignableFrom(source.javaClass)) {
                throw ResolverError("Expected source object to be an instance of '${this.genericType.getRawClass().name}' but instead got '${source.javaClass.name}'")
            }

            source
        })
    }
}

internal class MissingFieldResolver(field: FieldDefinition, options: SchemaParserOptions, private val inputValueClassMap: Map<InputValueDefinition, JavaType>, private val returnValueClass: JavaType): FieldResolver(field, FieldResolverScanner.Search(Any::class.java, MissingResolverInfo(), null), options, Any::class.java) {
    override fun scanForMatches(): List<TypeClassMatcher.PotentialMatch> = inputValueClassMap.map {
        TypeClassMatcher.PotentialMatch.parameterType(it.key.type, it.value, genericType, SchemaClassScanner.MethodParameterEmptyReference(), false)
    } + listOf(TypeClassMatcher.PotentialMatch.returnValue(field.type, returnValueClass, genericType, SchemaClassScanner.ReturnValueEmptyReference(), false))
    override fun createDataFetcher(): DataFetcher<*> = DataFetcher<Any> { null; }
}

internal typealias SourceResolver = (DataFetchingEnvironment) -> Any
