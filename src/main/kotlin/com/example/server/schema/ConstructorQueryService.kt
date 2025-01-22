package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class ConstructorQueryService : Query {
    suspend fun constructors() = ApiClient()
        .getConstructors()
}
