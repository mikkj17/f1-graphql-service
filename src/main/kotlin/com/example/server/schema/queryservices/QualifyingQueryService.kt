package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.result.Qualifying
import com.example.shared.mappers.toQualifying
import com.example.shared.mappers.toQualifyings
import com.expediagroup.graphql.server.operations.Query

class QualifyingQueryService(
    private val jolpicaClient: JolpicaClient
) : Query {
    private data class CacheKey(
        val year: Int?,
        val driverId: String?,
        val constructorId: String?,
        val circuitId: String?,
    )

    private val _qualifyingCache = mutableMapOf<Pair<Int, Int>, Qualifying>()
    private val _qualifyingsCache = mutableMapOf<CacheKey, List<Qualifying>>()

    suspend fun qualifying(year: Int, round: Int) = _qualifyingCache.getOrPut(year to round) {
        jolpicaClient
            .getQualifyings(year, round)
            .first()
            .toQualifying()
    }

    suspend fun qualifyings(
        year: Int? = null,
        driverId: String? = null,
        constructorId: String? = null,
        circuitId: String? = null,
    ): List<Qualifying> {
        require(listOf(year, driverId, constructorId, circuitId).count { it != null } == 1) {
            "exactly one of year, driverId, constructorId, circuitId must be provided."
        }

        return _qualifyingsCache.getOrPut(
            CacheKey(year, driverId, constructorId, circuitId)
        ) {
            when {
                year != null -> jolpicaClient.getQualifyings(year, null).toQualifyings()
                driverId != null -> jolpicaClient.getQualifyingsByDriver(driverId).toQualifyings()
                constructorId != null -> jolpicaClient.getQualifyingsByConstructor(constructorId).toQualifyings()
                circuitId != null -> jolpicaClient.getQualifyingsByCircuit(circuitId).toQualifyings()
                else -> throw AssertionError("unreachable")
            }
        }
    }
}
