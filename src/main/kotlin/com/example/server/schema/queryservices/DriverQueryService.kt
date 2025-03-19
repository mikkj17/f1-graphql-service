package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.client.openf1.OpenF1Client
import com.example.server.schema.models.driver.Driver
import com.example.server.schema.models.driver.DriverDetail
import com.example.shared.mappers.toDetailedDriver
import com.example.shared.mappers.toDriver
import com.expediagroup.graphql.server.operations.Query

class DriverQueryService : Query {
    private val driverListCache = mutableMapOf<Triple<Int?, Int?, String?>, List<Driver>>()
    private val driverCache = mutableMapOf<String, DriverDetail?>()

    suspend fun drivers(year: Int? = null, round: Int? = null, constructorId: String? = null): List<Driver> {
        require(!(constructorId != null && (year != null || round != null))) {
            "constructorId can't be used with year or round"
        }

        require(round == null || year != null) {
            "round can't be used without year"
        }

        return driverListCache.getOrPut(Triple(year, round, constructorId)) {
            val client = JolpicaClient()
            val drivers = if (constructorId != null)
                client.getDriversByConstructor(constructorId)
            else
                client.getDrivers(year, round)

            drivers.map { it.toDriver() }
        }
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
