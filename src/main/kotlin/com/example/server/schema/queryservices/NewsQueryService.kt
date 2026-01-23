package com.example.server.schema.queryservices

import com.example.client.espn.EspnClient
import com.example.server.schema.models.news.NewsArticle
import com.example.shared.mappers.toNewsArticle
import com.expediagroup.graphql.server.operations.Query

class NewsQueryService(
    private val espnClient: EspnClient
) : Query {
    suspend fun news(): List<NewsArticle> {
        return espnClient.getNews().map { it.toNewsArticle() }
    }
}
