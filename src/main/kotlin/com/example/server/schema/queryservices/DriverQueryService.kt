package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.driver.Driver
import com.example.shared.mappers.toDriver
import com.expediagroup.graphql.server.operations.Query
import io.ktor.server.plugins.*

class DriverQueryService(
    private val jolpicaClient: JolpicaClient,
) : Query {
    private val driverCache = mutableMapOf<String, Driver>()
    private val driverListCache = mutableMapOf<Triple<Int?, Int?, String?>, List<Driver>>()

    suspend fun drivers(year: Int? = null, round: Int? = null, constructorId: String? = null): List<Driver> {
        require(!(constructorId != null && (year != null || round != null))) {
            "constructorId can't be used with year or round"
        }

        require(round == null || year != null) {
            "round can't be used without year"
        }

        return driverListCache.getOrPut(Triple(year, round, constructorId)) {
            val drivers = if (constructorId != null)
                jolpicaClient.getDriversByConstructor(constructorId)
            else
                jolpicaClient.getDrivers(year, round)

            drivers.map { it.toDriver() }
        }
    }

    suspend fun driver(driverId: String) = driverCache.getOrPut(driverId) {
        jolpicaClient.getDriver(driverId)?.toDriver() ?: throw NotFoundException("Driver $driverId was not found")
    }
}
