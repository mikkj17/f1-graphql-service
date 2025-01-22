package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class CircuitQueryService : Query {
    suspend fun circuits() = ApiClient()
        .getCircuits()
}
