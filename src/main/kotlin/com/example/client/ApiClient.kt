package com.example.client

import com.example.client.schema.ApiResponse
import com.example.client.schema.MrData
import com.example.client.schema.Table
import com.example.shared.schema.models.*
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

        return client.get(url.buildString()).body<ApiResponse<T>>()
    }

    private suspend inline fun <reified T : Model> fetchAll(
        endpoint: String,
        crossinline tableExtractor: (MrData<T>) -> Table<T>,
        crossinline modelExtractor: (Table<T>) -> List<T>,
        vararg pathParameters: String,
    ): List<T> {
        // Fetch the first chunk and total records
        val response: ApiResponse<T> = fetchChunk(endpoint, 0, pathParameters = pathParameters)
        val firstChunk = modelExtractor(tableExtractor(response.data))

        // Calculate offsets for the remaining chunks
        val offsets = (limit until response.data.total step limit)

        // Fetch remaining chunks in parallel
        val remainingChunks = coroutineScope {
            offsets.map { offset ->
                async(Dispatchers.IO) {
                    val chunk: ApiResponse<T> = fetchChunk(endpoint, offset, pathParameters = pathParameters)
                    modelExtractor(tableExtractor(chunk.data))
                }
            }.awaitAll()
        }

        // Combine all chunks
        return firstChunk + remainingChunks.flatten()
    }

    suspend fun getDrivers() = fetchAll<Driver>(
        "drivers",
        tableExtractor = { it.driverTable!! },
        modelExtractor = { it.drivers!! },
    )

    suspend fun getConstructors() = fetchAll<Constructor>(
        "constructors",
        tableExtractor = { it.constructorTable!! },
        modelExtractor = { it.constructors!! },
    )

    suspend fun getCircuits() = fetchAll<Circuit>(
        "circuits",
        tableExtractor = { it.circuitTable!! },
        modelExtractor = { it.circuits!! },
    )

    suspend fun getSeasons() = fetchAll<Season>(
        "seasons",
        tableExtractor = { it.seasonTable!! },
        modelExtractor = { it.seasons!! },
    )

    suspend fun getRace(year: Int, round: Int) = fetchAll<Race>(
        "results",
        tableExtractor = { it.raceTable!! },
        modelExtractor = { it.races!! },
        year.toString(),
        round.toString(),
    )
        .first()
}
