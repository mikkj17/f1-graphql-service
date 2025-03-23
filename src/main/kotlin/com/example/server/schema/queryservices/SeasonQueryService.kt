package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.season.Season
import com.example.shared.mappers.toSeason
import com.expediagroup.graphql.server.operations.Query

class SeasonQueryService(
    private val jolpicaClient: JolpicaClient
) : Query {
    private var _cache: List<Season>? = null

    suspend fun seasons() = _cache ?: jolpicaClient
        .getSeasons()
        .map { it.toSeason() }
        .also { _cache = it }
}
