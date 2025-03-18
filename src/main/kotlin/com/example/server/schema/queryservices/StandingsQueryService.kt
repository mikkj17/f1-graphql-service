package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.standings.ConstructorStandingList
import com.example.server.schema.models.standings.DriverStandingList
import com.example.shared.mappers.toConstructorStandingList
import com.example.shared.mappers.toDriverStandingList
import com.expediagroup.graphql.server.operations.Query

class StandingsQueryService : Query {
    private val _driverCache = mutableMapOf<Pair<Int, Int?>, DriverStandingList>()
    private val _constructorCache = mutableMapOf<Pair<Int, Int?>, ConstructorStandingList>()

    suspend fun driverStandings(year: Int, round: Int? = null) = _driverCache.getOrPut(year to round) {
        JolpicaClient()
            .getDriverStandings(year, round)
            .toDriverStandingList()
    }

    suspend fun constructorStandings(year: Int, round: Int? = null) = _constructorCache.getOrPut(year to round) {
        JolpicaClient()
            .getConstructorStandings(year, round)
            .toConstructorStandingList()
    }
}
