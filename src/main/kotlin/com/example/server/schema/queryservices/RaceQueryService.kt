package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.result.Race
import com.example.shared.mappers.toRace
import com.example.shared.mappers.toRaces
import com.expediagroup.graphql.server.operations.Query

class RaceQueryService : Query {
    private val _raceCache = mutableMapOf<Pair<Int, Int>, Race>()
    private val _racesCache = mutableMapOf<Triple<Int?, String?, String?>, List<Race>>()

    suspend fun race(year: Int, round: Int) = _raceCache.getOrPut(year to round) {
        JolpicaClient()
            .getRaces(year, round)
            .first()
            .toRace()
    }

    suspend fun races(
        year: Int? = null,
        driverId: String? = null,
        constructorId: String? = null,
    ): List<Race> {
        require(listOf(year, driverId, constructorId).count { it != null } == 1) {
            "exactly one of year, driverId, or constructorId must be provided."
        }

        return _racesCache.getOrPut(
            Triple(year, driverId, constructorId)
        ) {
            val client = JolpicaClient()
            when {
                year != null -> client.getRaces(year, null).toRaces()
                driverId != null -> client.getRacesByDriver(driverId).map { it.toRace() }
                constructorId != null -> client.getRacesByConstructor(constructorId).map { it.toRace() }
                else -> throw AssertionError("unreachable")
            }
        }
    }
}
