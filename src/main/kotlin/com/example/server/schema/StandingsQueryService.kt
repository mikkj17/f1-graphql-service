package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class StandingsQueryService : Query {
    suspend fun driverStandings(year: Int, round: Int? = null) = ApiClient()
        .getDriverStandings(year, round)
}
