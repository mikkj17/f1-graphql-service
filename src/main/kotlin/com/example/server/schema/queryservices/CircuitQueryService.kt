package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.circuit.Circuit
import com.example.shared.mappers.toCircuit

class CircuitQueryService : CachedQueryService<Circuit, Circuit>() {
    suspend fun circuits(year: Int? = null, round: Int? = null) = cache.getOrPut(year to round) {
        JolpicaClient()
            .getCircuits(year, round)
            .map { it.toCircuit() }
    }

    suspend fun circuit(circuitId: String) = idCache.getOrPut(circuitId) {
        JolpicaClient()
            .getCircuit(circuitId)
            .toCircuit()
    }
}
