package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.circuit.Circuit

class CircuitQueryService : CachedQueryService<Circuit>() {
    suspend fun circuits(year: Int? = null, round: Int? = null) = cache.getOrPut(year to round) {
        ApiClient().getCircuits(year, round)
    }

    suspend fun circuit(circuitId: String) = idCache.getOrPut(circuitId) {
        ApiClient().getCircuit(circuitId)
    }
}
