package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ScheduleQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val scheduleQueryService = ScheduleQueryService(jolpicaClient)

    @Test
    fun `schedules should return list of schedules for year`() {
        runBlocking {
            coEvery { jolpicaClient.getSchedules(2023, null) } returns emptyList()

            val result = scheduleQueryService.schedules(year = 2023)

            assertEquals(0, result.size)
        }
    }
}
