package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.result.Qualifying
import com.expediagroup.graphql.server.operations.Query

class QualifyingQueryService : Query {
    private val _cache = mutableMapOf<Pair<Int, Int>, Qualifying>()

    suspend fun qualifying(year: Int, round: Int) = _cache.getOrPut(year to round) {
        ApiClient().getQualifying(year, round)
    }
}
