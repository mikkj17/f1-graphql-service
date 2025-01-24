package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class ScheduleQueryService : Query {
    suspend fun schedules(year: Int) = ApiClient()
        .getSchedules(year)
}
