package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class CircuitQueryService : Query {
    suspend fun circuits(year: Int? = null, round: Int? = null) = ApiClient()
        .getCircuits(year, round)

    suspend fun circuit(circuitId: String) = ApiClient()
        .getCircuit(circuitId)
}
