package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.ktor.server.plugins.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.schedule.Schedule as JolpicaSchedule

class ScheduleQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val scheduleQueryService = ScheduleQueryService(jolpicaClient)

    private val sampleJolpicaSchedule = JolpicaSchedule(
        season = 2023,
        round = 1,
        url = "https://test.com/bahrain",
        raceName = "Bahrain Grand Prix",
        circuit = JolpicaCircuit(
            id = "sakhir",
            url = "https://test.com/sakhir",
            name = "Bahrain International Circuit",
            location = JolpicaLocation(
                latitude = 26.0325f,
                longitude = 50.5106f,
                locality = "Sakhir",
                country = "Bahrain"
            )
        ),
        date = "2023-03-05"
    )

    @Test
    fun `schedule should return schedule for year and round`() {
        runBlocking {
            coEvery { jolpicaClient.getSchedules(2023, 1) } returns listOf(sampleJolpicaSchedule)

            val result = scheduleQueryService.schedule(2023, 1)

            assertEquals(2023, result.season)
            assertEquals(1, result.round)
            assertEquals("Bahrain Grand Prix", result.raceName)
        }
    }

    @Test
    fun `schedule should throw NotFoundException when no schedule found`() {
        runBlocking {
            coEvery { jolpicaClient.getSchedules(2023, 1) } returns emptyList()

            assertFailsWith<NotFoundException> {
                scheduleQueryService.schedule(2023, 1)
            }
        }
    }

    @Test
    fun `schedules should return list of schedules for year`() {
        runBlocking {
            coEvery { jolpicaClient.getSchedules(2023, null) } returns listOf(sampleJolpicaSchedule)

            val result = scheduleQueryService.schedules(year = 2023)

            assertEquals(1, result.size)
            assertEquals(2023, result[0].season)
        }
    }

    @Test
    fun `schedules should return schedules by driverId`() {
        runBlocking {
            coEvery { jolpicaClient.getSchedulesByDriver("leclerc") } returns listOf(sampleJolpicaSchedule)

            val result = scheduleQueryService.schedules(driverId = "leclerc")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getSchedulesByDriver("leclerc") }
        }
    }

    @Test
    fun `schedules should return schedules by constructorId`() {
        runBlocking {
            coEvery { jolpicaClient.getSchedulesByConstructor("ferrari") } returns listOf(sampleJolpicaSchedule)

            val result = scheduleQueryService.schedules(constructorId = "ferrari")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getSchedulesByConstructor("ferrari") }
        }
    }

    @Test
    fun `schedules should use cache`() {
        runBlocking {
            coEvery { jolpicaClient.getSchedules(2023, null) } returns listOf(sampleJolpicaSchedule)

            scheduleQueryService.schedules(year = 2023)
            scheduleQueryService.schedules(year = 2023)

            coVerify(exactly = 1) { jolpicaClient.getSchedules(2023, null) }
        }
    }

    @Test
    fun `schedules should throw error if multiple parameters provided`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                scheduleQueryService.schedules(year = 2023, driverId = "leclerc")
            }
        }
    }

    @Test
    fun `schedules should throw error if no parameters provided`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                scheduleQueryService.schedules()
            }
        }
    }
}
