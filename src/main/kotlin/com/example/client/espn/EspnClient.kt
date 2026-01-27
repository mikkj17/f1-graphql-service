package com.example.client.espn

import com.example.client.espn.schema.models.EspnArticle
import com.example.client.espn.schema.models.EspnNewsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class EspnClient(private val client: HttpClient) : IEspnClient {
    private val baseUrl = "https://site.api.espn.com/apis/site/v2/sports/racing/f1/news"

    override suspend fun getNews(): List<EspnArticle> {
        val response: EspnNewsResponse = client.get(baseUrl).body()
        return response.articles
    }
}
