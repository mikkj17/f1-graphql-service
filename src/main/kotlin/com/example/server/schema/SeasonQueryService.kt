package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.Season
import com.expediagroup.graphql.server.operations.Query

class SeasonQueryService : Query {
    suspend fun seasons(): List<Season> {
        return ApiClient()
            .getSeasons()
    }
}
