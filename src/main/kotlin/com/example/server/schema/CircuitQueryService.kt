package com.example.server.schema

import com.example.client.ApiClient
import com.example.server.schema.models.Circuit
import com.expediagroup.graphql.server.operations.Query

class CircuitQueryService : Query {
    suspend fun circuits(): List<Circuit> {
        return ApiClient()
            .getCircuits()
            .map { it.toCircuit() }
    }
}
