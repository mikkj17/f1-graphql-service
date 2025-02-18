package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.driver.Driver

class DriverQueryService : CachedQueryService<Driver>() {
    suspend fun drivers(year: Int? = null, round: Int? = null) = cache.getOrPut(year to round) {
        ApiClient().getDrivers(year, round)
    }

    suspend fun driver(driverId: String) = idCache.getOrPut(driverId) {
        ApiClient().getDriver(driverId)
    }
}
