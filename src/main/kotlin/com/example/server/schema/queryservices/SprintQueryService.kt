package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import com.example.server.schema.models.result.Sprint
import com.example.shared.mappers.toSprint
import com.example.shared.mappers.toSprints
import com.expediagroup.graphql.server.operations.Query
import io.ktor.server.plugins.*

class SprintQueryService(
    private val jolpicaClient: IJolpicaClient
) : Query {
    private val _sprintCache = mutableMapOf<Pair<Int, Int>, Sprint>()
    private val _sprintsCache = mutableMapOf<Int, List<Sprint>>()

    suspend fun sprint(year: Int, round: Int) = _sprintCache.getOrPut(year to round) {
        jolpicaClient
            .getSprints(year, round)
            .firstOrNull()
            ?.toSprint()
            ?: throw NotFoundException("No sprint found for $year, $round")
    }

    suspend fun sprints(year: Int) = _sprintsCache.getOrPut(year) {
        jolpicaClient
            .getSprints(year, null)
            .toSprints()
    }
}
