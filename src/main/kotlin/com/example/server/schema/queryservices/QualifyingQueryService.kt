package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.result.Qualifying
import com.example.shared.mappers.toQualifying
import com.expediagroup.graphql.server.operations.Query

class QualifyingQueryService : Query {
    private val _cache = mutableMapOf<Pair<Int, Int>, Qualifying>()

    suspend fun qualifying(year: Int, round: Int) = _cache.getOrPut(year to round) {
        JolpicaClient()
            .getQualifying(year, round)
            .toQualifying()
    }
}
