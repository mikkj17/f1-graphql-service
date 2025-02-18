package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.result.Race
import com.expediagroup.graphql.server.operations.Query

class RaceQueryService : Query {
    private val _cache = mutableMapOf<Pair<Int, Int>, Race>()

    suspend fun race(year: Int, round: Int) = _cache.getOrPut(year to round) {
        ApiClient().getRace(year, round)
    }
}
