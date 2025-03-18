package com.example.client.openf1

import com.example.client.openf1.schema.models.OpenF1Model
import com.example.client.openf1.schema.models.driver.Driver
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

class OpenF1Client {
    private val host = "api.openf1.org"
    private val path = "/v1"

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                namingStrategy = JsonNamingStrategy.SnakeCase
            })
        }
    }

    private suspend inline fun <reified T : OpenF1Model> fetch(
        endpoint: String,
        params: Map<String, String>
    ): List<T> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = this@OpenF1Client.host
            path(path, endpoint)
            for ((key, value) in params.entries) {
                parameters.append(key, value)
            }
        }

        return client
            .get(url.buildString())
            .body<List<T>>()
    }

    suspend fun getDriver(nameAcronym: String) = fetch<Driver>(
        "drivers",
        mapOf(
            "meeting_key" to "latest",
            "name_acronym" to nameAcronym,
        )
    )
        .firstOrNull()
}
