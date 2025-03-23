package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.circuit.Circuit
import com.example.shared.mappers.toCircuit
import com.expediagroup.graphql.server.operations.Query

class CircuitQueryService(
    private val jolpicaClient: JolpicaClient
) : Query {
    private val _cache = mutableMapOf<Pair<Int?, Int?>, List<Circuit>>()

    suspend fun circuits(year: Int? = null, round: Int? = null) = _cache.getOrPut(year to round) {
        jolpicaClient
            .getCircuits(year, round)
            .map { it.toCircuit() }
    }
}
