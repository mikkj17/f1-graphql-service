package com.example.client.openf1

import com.example.client.openf1.schema.models.OpenF1Model
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class OpenF1Client(
    private val client: HttpClient
) {
    private val host = "api.openf1.org"
    private val path = "/v1"

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
}
