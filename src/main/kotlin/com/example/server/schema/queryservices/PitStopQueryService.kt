package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.pitstop.RacePitStop
import com.example.shared.mappers.toRacePitStop
import com.expediagroup.graphql.server.operations.Query
import io.ktor.server.plugins.*

class PitStopQueryService(
    private val jolpicaClient: JolpicaClient
) : Query {
    private val _cache = mutableMapOf<Triple<Int, Int, List<String>?>, RacePitStop>()

    suspend fun pitStops(year: Int, round: Int, drivers: List<String>? = null): RacePitStop {
        return _cache.getOrPut(Triple(year, round, drivers)) {
            jolpicaClient
                .getPitStops(year, round)
                ?.toRacePitStop(drivers)
                ?: throw NotFoundException("No pit stops found for $year, $round, $drivers")
        }
    }
}
