package graphql.kickstart.tools

import graphql.schema.GraphQLCodeRegistry
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import graphql.schema.GraphQLType
import graphql.schema.GraphQLDirective
import graphql.schema.visibility.NoIntrospectionGraphqlFieldVisibility
import graphql.schema.*

/**
 * @author Andrew Potter
 */
data class SchemaObjects(
    val query: GraphQLObjectType,
    val mutation: GraphQLObjectType?,
    val subscription: GraphQLObjectType?,
    val dictionary: Set<GraphQLType>,
    val directives: Set<GraphQLDirective>,
    val codeRegistryBuilder: GraphQLCodeRegistry.Builder,
    val description: String?,
    val customDirectiveSet: Set<GraphQLDirective>
) {
    /**
     * Makes a GraphQLSchema with query, mutation and subscription.
     */
    fun toSchema(): GraphQLSchema {
        return GraphQLSchema.newSchema()
            .description(description)
            .query(query)
            .mutation(mutation)
            .subscription(subscription)
            .additionalTypes(dictionary)
            .additionalDirectives(directives)
            .codeRegistry(codeRegistryBuilder.build())
            // 如果没问题，那么整个customDirective的改动都可以删除
//            .additionalDirectives(customDirectiveSet)
            .build()
    }

    /**
     * Makes a GraphQLSchema with query but without mutation and subscription.
     */
    fun toReadOnlySchema(): GraphQLSchema = GraphQLSchema.newSchema()
        .description(description)
        .query(query)
        .additionalTypes(dictionary)
        .build()
}
