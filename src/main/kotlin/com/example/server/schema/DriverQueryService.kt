package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class DriverQueryService : Query {
    suspend fun drivers(year: Int? = null, round: Int? = null) = ApiClient()
        .getDrivers(year, round)

    suspend fun driver(driverId: String) = ApiClient()
        .getDriver(driverId)
}
