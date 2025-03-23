package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.lap.RaceLaps
import com.example.shared.mappers.toRaceLaps
import com.expediagroup.graphql.server.operations.Query

class LapQueryService(
    private val jolpicaClient: JolpicaClient
) : Query {
    private val _cache = mutableMapOf<Triple<Int, Int, List<String>?>, RaceLaps>()

    suspend fun laps(year: Int, round: Int, drivers: List<String>? = null): RaceLaps {
        return _cache.getOrPut(Triple(year, round, drivers)) {
            jolpicaClient
                .getLaps(year, round)
                .toRaceLaps(drivers)
        }
    }
}
