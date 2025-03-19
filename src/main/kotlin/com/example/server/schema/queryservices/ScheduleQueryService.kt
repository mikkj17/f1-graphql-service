package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.schedule.Schedule
import com.example.shared.mappers.toSchedule
import com.expediagroup.graphql.server.operations.Query

class ScheduleQueryService : Query {
    private val cache = mutableMapOf<Triple<Int?, String?, String?>, List<Schedule>>()

    suspend fun schedules(
        year: Int? = null,
        driverId: String? = null,
        constructorId: String? = null
    ): List<Schedule> {
        require(listOf(year, driverId, constructorId).count { it != null } == 1) {
            "exactly one of year, driverId, or constructorId must be provided."
        }

        return cache.getOrPut(
            Triple(year, driverId, constructorId)
        ) {
            val client = JolpicaClient()
            val schedules = when {
                year != null -> client.getSchedules(year)
                driverId != null -> client.getSchedulesByDriver(driverId)
                constructorId != null -> client.getSchedulesByConstructor(constructorId)
                else -> throw AssertionError("unreachable")
            }

            schedules.map { it.toSchedule() }
        }
    }
}
