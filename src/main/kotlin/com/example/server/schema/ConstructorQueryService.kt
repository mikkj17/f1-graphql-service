package com.example.server.schema

import com.example.client.ApiClient
import com.example.server.schema.models.Constructor
import com.expediagroup.graphql.server.operations.Query

class ConstructorQueryService : Query {
    suspend fun constructors(): List<Constructor> {
        return ApiClient()
            .getConstructors()
            .map { it.toConstructor() }
    }
}
