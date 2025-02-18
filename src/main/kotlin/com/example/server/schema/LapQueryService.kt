package com.example.server.schema

import com.example.client.ApiClient
import com.example.server.schema.models.RaceLaps
import com.example.shared.schema.models.lap.Lap
import com.expediagroup.graphql.server.operations.Query

class LapQueryService : Query {
    private val _cache = mutableMapOf<Triple<Int, Int, String?>, RaceLaps>()

    suspend fun laps(year: Int, round: Int, driverId: String? = null): RaceLaps {
        return _cache.getOrPut(Triple(year, round, driverId)) {
            val raceLaps = ApiClient().getLaps(year, round)
            val race = raceLaps.first()

            val laps = raceLaps
                .flatMap { it.laps }
                .groupBy { it.number }
                .map { (number, laps) ->
                    Lap(
                        number = number,
                        timings = laps
                            .flatMap { it.timings }
                            .filter { driverId == null || it.driverId == driverId }
                    )
                }

            RaceLaps(
                season = race.season,
                round = race.round,
                url = race.url,
                raceName = race.raceName,
                circuit = race.circuit,
                date = race.date,
                time = race.time,
                laps = laps,
            )
        }
    }
}
