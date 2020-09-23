package graphql.kickstart.tools

import graphql.schema.*
import graphql.schema.visibility.NoIntrospectionGraphqlFieldVisibility

/**
 * @author Andrew Potter
 */
data class SchemaObjects(val query: GraphQLObjectType, val mutation: GraphQLObjectType?, val subscription: GraphQLObjectType?, val dictionary: Set<GraphQLType>, val codeRegistryBuilder: GraphQLCodeRegistry.Builder, val customDirectiveSet: Set<GraphQLDirective>) {

    /**
     * Makes a GraphQLSchema with query, mutation and subscription.
     */
    fun toSchema(introspectionEnabled: Boolean): GraphQLSchema {
        if (!introspectionEnabled) {
            codeRegistryBuilder.fieldVisibility(NoIntrospectionGraphqlFieldVisibility.NO_INTROSPECTION_FIELD_VISIBILITY)
        }

        return GraphQLSchema.newSchema()
                .query(query)
                .mutation(mutation)
                .subscription(subscription)
                .additionalTypes(dictionary)
                .codeRegistry(codeRegistryBuilder.build())
                .additionalDirectives(customDirectiveSet)
                .build()
    }

    /**
     * Makes a GraphQLSchema with query but without mutation and subscription.
     */
    @Suppress("unused")
    fun toReadOnlySchema(): GraphQLSchema = GraphQLSchema.newSchema()
            .query(query)
            .additionalTypes(dictionary)
            .build()
}
