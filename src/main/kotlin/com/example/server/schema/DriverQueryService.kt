package com.example.server.schema

import com.example.client.ApiClient
import com.example.server.schema.models.Driver
import com.expediagroup.graphql.server.operations.Query

class DriverQueryService : Query {
    suspend fun drivers(): List<Driver> {
        return ApiClient()
            .getDrivers()
            .map { it.toDriver() }
    }
}
