package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.standings.ConstructorStandingList
import com.example.shared.schema.models.standings.DriverStandingList
import com.expediagroup.graphql.server.operations.Query

class StandingsQueryService : Query {
    private val _driverCache = mutableMapOf<Pair<Int, Int?>, DriverStandingList>()
    private val _constructorCache = mutableMapOf<Pair<Int, Int?>, ConstructorStandingList>()

    suspend fun driverStandings(year: Int, round: Int? = null) = _driverCache.getOrPut(year to round) {
        ApiClient().getDriverStandings(year, round)
    }

    suspend fun constructorStandings(year: Int, round: Int? = null) = _constructorCache.getOrPut(year to round) {
        ApiClient().getConstructorStandings(year, round)
    }
}
