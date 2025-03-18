package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.schedule.Schedule
import com.example.shared.mappers.toSchedule

class ScheduleQueryService : CachedQueryService<Schedule, Schedule>() {
    suspend fun schedules(year: Int) = cache.getOrPut(year to null) {
        JolpicaClient()
            .getSchedules(year)
            .map { it.toSchedule() }
    }
}
