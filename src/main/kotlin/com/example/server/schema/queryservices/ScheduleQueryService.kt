package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import com.example.server.schema.models.schedule.Schedule
import com.example.shared.mappers.toSchedule
import com.expediagroup.graphql.server.operations.Query
import io.ktor.server.plugins.*

class ScheduleQueryService(
    private val jolpicaClient: IJolpicaClient
) : Query {
    private val _scheduleCache = mutableMapOf<Pair<Int, Int>, Schedule>()
    private val _schedulesCache = mutableMapOf<Triple<Int?, String?, String?>, List<Schedule>>()

    suspend fun schedule(year: Int, round: Int) = _scheduleCache.getOrPut(Pair(year, round)) {
        jolpicaClient
            .getSchedules(year, round)
            .firstOrNull()
            ?.toSchedule()
            ?: throw NotFoundException("No schedule found for year $year and round $round")
    }

    suspend fun schedules(
        year: Int? = null,
        driverId: String? = null,
        constructorId: String? = null
    ): List<Schedule> {
        require(listOf(year, driverId, constructorId).count { it != null } == 1) {
            "exactly one of year, driverId, or constructorId must be provided."
        }

        return _schedulesCache.getOrPut(
            Triple(year, driverId, constructorId)
        ) {
            val schedules = when {
                year != null -> jolpicaClient.getSchedules(year, null)
                driverId != null -> jolpicaClient.getSchedulesByDriver(driverId)
                constructorId != null -> jolpicaClient.getSchedulesByConstructor(constructorId)
                else -> throw AssertionError("unreachable")
            }

            schedules.map { it.toSchedule() }
        }
    }
}
