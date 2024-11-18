package com.example.client

import com.example.client.schema.ApiResponse
import com.example.client.schema.models.Driver
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*


class ApiClient {
    private val baseUrl = "https://api.jolpi.ca/ergast/f1"
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    suspend fun getDrivers(): ApiResponse<Driver> {
        return client.get("${baseUrl}/drivers.json?limit=1000").body()
    }
}
