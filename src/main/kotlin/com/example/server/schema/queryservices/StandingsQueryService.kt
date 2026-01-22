package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import com.example.server.schema.models.standings.ConstructorStandingList
import com.example.server.schema.models.standings.DriverStandingList
import com.example.shared.mappers.toConstructorStandingList
import com.example.shared.mappers.toDriverStandingList
import com.expediagroup.graphql.server.operations.Query
import io.ktor.server.plugins.*

class StandingsQueryService(
    private val jolpicaClient: IJolpicaClient
) : Query {
    private val _driverCache = mutableMapOf<Pair<Int, Int?>, DriverStandingList>()
    private val _constructorCache = mutableMapOf<Pair<Int, Int?>, ConstructorStandingList>()

    suspend fun driverStandings(year: Int, round: Int? = null) = _driverCache.getOrPut(year to round) {
        jolpicaClient
            .getDriverStandings(year, round)
            ?.toDriverStandingList()
            ?: throw NotFoundException("No driver standings found for $year, $round")
    }

    suspend fun constructorStandings(year: Int, round: Int? = null) = _constructorCache.getOrPut(year to round) {
        jolpicaClient
            .getConstructorStandings(year, round)
            ?.toConstructorStandingList()
            ?: throw NotFoundException("No constructor standings found for $year, $round")
    }
}
