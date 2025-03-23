package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.circuit.Circuit
import com.example.shared.mappers.toCircuit

class CircuitQueryService(
    private val jolpicaClient: JolpicaClient
) : CachedQueryService<Circuit, Circuit>() {
    suspend fun circuits(year: Int? = null, round: Int? = null) = cache.getOrPut(year to round) {
        jolpicaClient
            .getCircuits(year, round)
            .map { it.toCircuit() }
    }
}
