package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.schedule.Schedule

class ScheduleQueryService : CachedQueryService<Schedule>() {
    suspend fun schedules(year: Int) = cache.getOrPut(year to null) {
        ApiClient().getSchedules(year)
    }
}
