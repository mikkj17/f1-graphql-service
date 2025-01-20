package com.example.client

import com.example.client.schema.ApiResponse
import com.example.client.schema.MrData
import com.example.client.schema.Table
import com.example.client.schema.models.Constructor
import com.example.client.schema.models.Driver
import com.example.client.schema.models.Model
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ApiClient {
    private val baseUrl = "https://api.jolpi.ca/ergast/f1"
    private val limit = 100

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    private suspend inline fun <reified T : Model> fetchChunk(
        endpoint: String,
        offset: Int,
    ): ApiResponse<T> {
        return client.get("$baseUrl/$endpoint.json?limit=$limit&offset=$offset").body<ApiResponse<T>>()
    }

    private suspend inline fun <reified T : Model> fetchAll(
        endpoint: String,
        crossinline tableExtractor: (MrData<T>) -> Table<T>,
        crossinline modelExtractor: (Table<T>) -> List<T>,
    ): List<T> {
        // Fetch the first chunk and total records
        val response: ApiResponse<T> = fetchChunk(endpoint, 0)
        val firstChunk = modelExtractor(tableExtractor(response.data))

        // Calculate offsets for the remaining chunks
        val offsets = (limit until response.data.total step limit)

        // Fetch remaining chunks in parallel
        val remainingChunks = coroutineScope {
            offsets.map { offset ->
                async(Dispatchers.IO) {
                    val chunk: ApiResponse<T> = fetchChunk(endpoint, offset)
                    modelExtractor(tableExtractor(chunk.data))
                }
            }.awaitAll()
        }

        // Combine all chunks
        return firstChunk + remainingChunks.flatten()
    }

    suspend fun getDrivers() = fetchAll<Driver>(
        Driver.endpoint,
        tableExtractor = { Driver.getTable(it) },
        modelExtractor = { Driver.getModels(it) },
    )

    suspend fun getConstructors() = fetchAll<Constructor>(
        Constructor.endpoint,
        tableExtractor = { Constructor.getTable(it) },
        modelExtractor = { Constructor.getModels(it) },
    )
}
