package com.example.client

import com.example.client.schema.ApiResponse
import com.example.client.schema.MrData
import com.example.client.schema.Table
import com.example.shared.schema.models.Model
import com.example.shared.schema.models.circuit.Circuit
import com.example.shared.schema.models.constructor.Constructor
import com.example.shared.schema.models.driver.Driver
import com.example.shared.schema.models.result.Qualifying
import com.example.shared.schema.models.result.Race
import com.example.shared.schema.models.schedule.Schedule
import com.example.shared.schema.models.season.Season
import com.example.shared.schema.models.standings.DriverStandingList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

typealias TableExtractor<T> = MrData<T>.() -> Table<T>
typealias ModelsExtractor<T> = Table<T>.() -> List<T>

class ApiClient {
    private val host = "api.jolpi.ca"
    private val path = "/ergast/f1"
    private val limit = 100

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    private suspend inline fun <reified T : Model> fetchChunk(
        endpoint: String,
        offset: Int,
        vararg pathParameters: String,
    ): ApiResponse<T> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = this@ApiClient.host
            path(path, *pathParameters, endpoint)
            parameters.append("limit", limit.toString())
            parameters.append("offset", offset.toString())
        }

        return client.get(url.buildString()).body()
    }

    private suspend inline fun <reified T : Model> fetch(
        endpoint: String,
        crossinline getTable: TableExtractor<T>,
        crossinline getModels: ModelsExtractor<T>,
        vararg pathParameters: String,
    ): T {
        val response: ApiResponse<T> = fetchChunk(endpoint, 0, pathParameters = pathParameters)
        return response
            .data
            .getTable()
            .getModels()
            .first()
    }

    private suspend inline fun <reified T : Model> fetchAll(
        endpoint: String,
        crossinline getTable: TableExtractor<T>,
        crossinline getModels: ModelsExtractor<T>,
        vararg pathParameters: String,
    ): List<T> {
        // Fetch the first chunk and total records
        val response: ApiResponse<T> = fetchChunk(endpoint, 0, pathParameters = pathParameters)
        val firstChunk = response.data.getTable().getModels()

        // Calculate offsets for the remaining chunks
        val offsets = (limit until response.data.total step limit)

        // Fetch remaining chunks in parallel
        val remainingChunks = coroutineScope {
            offsets.map { offset ->
                async(Dispatchers.IO) {
                    val chunk: ApiResponse<T> = fetchChunk(endpoint, offset, pathParameters = pathParameters)
                    chunk.data.getTable().getModels()
                }
            }.awaitAll()
        }

        // Combine all chunks
        return firstChunk + remainingChunks.flatten()
    }

    suspend fun getDrivers() = fetchAll<Driver>(
        "drivers",
        getTable = { driverTable!! },
        getModels = { drivers!! },
    )

    suspend fun getConstructors() = fetchAll<Constructor>(
        "constructors",
        getTable = { constructorTable!! },
        getModels = { constructors!! },
    )

    suspend fun getCircuits() = fetchAll<Circuit>(
        "circuits",
        getTable = { circuitTable!! },
        getModels = { circuits!! },
    )

    suspend fun getSeasons() = fetchAll<Season>(
        "seasons",
        getTable = { seasonTable!! },
        getModels = { seasons!! },
    )

    suspend fun getRace(year: Int, round: Int) = fetch<Race>(
        "results",
        getTable = { raceTable!! },
        getModels = { races!! },
        year.toString(),
        round.toString(),
    )

    suspend fun getQualifying(year: Int, round: Int) = fetch<Qualifying>(
        "qualifying",
        getTable = { raceTable!! },
        getModels = { races!! },
        year.toString(),
        round.toString(),
    )

    suspend fun getSchedules(year: Int) = fetchAll<Schedule>(
        "",
        getTable = { raceTable!! },
        getModels = { races!! },
        year.toString(),
    )

    suspend fun getDriverStandings(year: Int, round: Int?) = fetch<DriverStandingList>(
        "driverStandings",
        getTable = { standingsTable!! },
        getModels = { standingsLists!! },
        pathParameters = listOfNotNull(year.toString(), round?.toString()).toTypedArray()
    )
}
