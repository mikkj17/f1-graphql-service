package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class SeasonQueryService : Query {
    suspend fun seasons() = ApiClient()
        .getSeasons()
}
