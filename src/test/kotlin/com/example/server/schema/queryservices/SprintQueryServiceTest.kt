package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class SprintQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val sprintQueryService = SprintQueryService(jolpicaClient)

    @Test
    fun `sprints should return list of sprints for year`() {
        runBlocking {
            coEvery { jolpicaClient.getSprints(2023, null) } returns emptyList()

            val result = sprintQueryService.sprints(2023)

            assertEquals(0, result.size)
        }
    }
}
