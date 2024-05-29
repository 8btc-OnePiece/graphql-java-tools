package graphql.kickstart.tools.resolver

import graphql.kickstart.tools.MissingResolverInfo
import graphql.kickstart.tools.SchemaParserOptions
import graphql.kickstart.tools.TypeClassMatcher
import graphql.language.FieldDefinition
import graphql.schema.DataFetcher

internal class MissingFieldResolver(
    field: FieldDefinition,
    options: SchemaParserOptions
) : FieldResolver(field, FieldResolverScanner.Search(Any::class.java, MissingResolverInfo(), null), options, Any::class.java) {

    private val missingResolverDataFetcherProvider: MissingResolverDataFetcherProvider = options.missingResolverDataFetcherProvider
            ?: MissingResolverDataFetcherProvider {
                _, options -> options.missingResolverDataFetcher ?: DataFetcher<Any> { TODO("Schema resolver not implemented") }
            }
    override fun scanForMatches(): List<TypeClassMatcher.PotentialMatch> = listOf()
    override fun createDataFetcher(): DataFetcher<*> = missingResolverDataFetcherProvider.createDataFetcher(field, options)
    //    // offer necessary javaTypes matching schema
//    override fun scanForMatches(): List<TypeClassMatcher.PotentialMatch> = inputValueClassMap.map {
//        TypeClassMatcher.PotentialMatch.parameterType(it.key.type, it.value, genericType, SchemaClassScanner.MethodParameterEmptyReference(), false)
//    } + listOf(TypeClassMatcher.PotentialMatch.returnValue(field.type, returnValueClass, genericType, SchemaClassScanner.ReturnValueEmptyReference(), false))
//    // return null instead of an error message
//    override fun createDataFetcher(): DataFetcher<*> = DataFetcher<Any> { null; }
}
