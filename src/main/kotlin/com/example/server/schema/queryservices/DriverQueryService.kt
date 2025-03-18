package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.client.openf1.OpenF1Client
import com.example.server.schema.models.driver.Driver
import com.example.server.schema.models.driver.DriverDetail
import com.example.shared.mappers.toDetailedDriver
import com.example.shared.mappers.toDriver
import com.expediagroup.graphql.server.operations.Query

class DriverQueryService : Query {
    private val driverListCache = mutableMapOf<Pair<Int?, Int?>, List<Driver>>()
    private val driverCache = mutableMapOf<String, DriverDetail?>()

    suspend fun drivers(year: Int? = null, round: Int? = null) = driverListCache.getOrPut(year to round) {
        JolpicaClient()
            .getDrivers(year, round)
            .map { it.toDriver() }
    }

    suspend fun driver(driverId: String): DriverDetail? {
        return driverCache.getOrPut(driverId) {
            JolpicaClient().getDriver(driverId)
                .takeIf { it.code != null }
                ?.let { driver ->
                    OpenF1Client().getDriver(driver.code!!)
                        ?.let(driver::toDetailedDriver)
                }
        }
    }
}
