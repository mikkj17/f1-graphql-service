package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.result.Race
import com.example.shared.mappers.toRace
import com.example.shared.mappers.toRaces
import com.expediagroup.graphql.server.operations.Query

class RaceQueryService(
    private val jolpicaClient: JolpicaClient
) : Query {
    private data class CacheKey(
        val year: Int?,
        val driverId: String?,
        val constructorId: String?,
        val circuitId: String?,
    )

    private val _raceCache = mutableMapOf<Pair<Int, Int>, Race?>()
    private val _racesCache = mutableMapOf<CacheKey, List<Race>>()

    suspend fun race(year: Int, round: Int) = _raceCache.getOrPut(year to round) {
        jolpicaClient
            .getRaces(year, round)
            .firstOrNull()
            ?.toRace()
    }

    suspend fun races(
        year: Int? = null,
        driverId: String? = null,
        constructorId: String? = null,
        circuitId: String? = null,
    ): List<Race> {
        require(listOf(year, driverId, constructorId, circuitId).count { it != null } == 1) {
            "exactly one of year, driverId, constructorId, or circuitId must be provided."
        }

        return _racesCache.getOrPut(
            CacheKey(year, driverId, constructorId, circuitId)
        ) {
            when {
                year != null -> jolpicaClient.getRaces(year, null).toRaces()
                driverId != null -> jolpicaClient.getRacesByDriver(driverId).toRaces()
                constructorId != null -> jolpicaClient.getRacesByConstructor(constructorId).toRaces()
                circuitId != null -> jolpicaClient.getRacesByCircuit(circuitId).toRaces()
                else -> throw AssertionError("unreachable")
            }
        }
    }

    suspend fun mostRecentRace() = jolpicaClient
        .getMostRecentRace()
        ?.toRace()
}
