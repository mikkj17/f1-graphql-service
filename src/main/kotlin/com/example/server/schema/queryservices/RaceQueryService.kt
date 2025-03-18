package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.result.Race
import com.example.shared.mappers.toRace
import com.expediagroup.graphql.server.operations.Query

class RaceQueryService : Query {
    private val _cache = mutableMapOf<Pair<Int, Int>, Race>()

    suspend fun race(year: Int, round: Int) = _cache.getOrPut(year to round) {
        JolpicaClient()
            .getRace(year, round)
            .toRace()
    }
}
