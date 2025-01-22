package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class DriverQueryService : Query {
    suspend fun drivers() = ApiClient()
        .getDrivers()
}
