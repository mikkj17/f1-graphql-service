package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.result.Qualifying
import com.example.shared.mappers.toQualifying
import com.example.shared.mappers.toQualifyings
import com.expediagroup.graphql.server.operations.Query

class QualifyingQueryService : Query {
    private val _qualifyingCache = mutableMapOf<Pair<Int, Int>, Qualifying>()
    private val _qualifyingsCache = mutableMapOf<Triple<Int?, String?, String?>, List<Qualifying>>()

    suspend fun qualifying(year: Int, round: Int) = _qualifyingCache.getOrPut(year to round) {
        JolpicaClient()
            .getQualifyings(year, round)
            .first()
            .toQualifying()
    }

    suspend fun qualifyings(
        year: Int? = null,
        driverId: String? = null,
        constructorId: String? = null,
    ): List<Qualifying> {
        require(listOf(year, driverId, constructorId).count { it != null } == 1) {
            "exactly one of year, driverId, or constructorId must be provided."
        }

        return _qualifyingsCache.getOrPut(
            Triple(year, driverId, constructorId)
        ) {
            val client = JolpicaClient()
            when {
                year != null -> client.getQualifyings(year, null).toQualifyings()
                driverId != null -> client.getQualifyingsByDriver(driverId).map { it.toQualifying() }
                constructorId != null -> client.getQualifyingsByConstructor(constructorId).map { it.toQualifying() }
                else -> throw AssertionError("unreachable")
            }
        }
    }
}
