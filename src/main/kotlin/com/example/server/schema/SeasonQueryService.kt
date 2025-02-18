package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.season.Season

class SeasonQueryService : CachedQueryService<Season>() {
    suspend fun seasons() = cache.getOrPut(null to null) {
        ApiClient().getSeasons()
    }
}
