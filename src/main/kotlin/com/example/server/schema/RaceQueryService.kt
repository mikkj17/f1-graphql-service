package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class RaceQueryService : Query {
    suspend fun race(year: Int, round: Int) = ApiClient()
        .getRace(year, round)
}
