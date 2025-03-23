package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.season.Season
import com.example.shared.mappers.toSeason

class SeasonQueryService(
    private val jolpicaClient: JolpicaClient
) : CachedQueryService<Season, Season>() {
    suspend fun seasons() = cache.getOrPut(null to null) {
        jolpicaClient
            .getSeasons()
            .map { it.toSeason() }
    }
}
