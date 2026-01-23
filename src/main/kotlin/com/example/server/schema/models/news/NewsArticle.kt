package com.example.server.schema.models.news

data class NewsArticle(
    val id: Long,
    val headline: String,
    val description: String,
    val lastModified: String,
    val published: String,
    val images: List<NewsImage>,
    val links: NewsLinks?
)

data class NewsImage(
    val id: Long,
    val url: String,
    val caption: String?,
    val credit: String?
)

data class NewsLinks(
    val web: String?,
    val mobile: String?
)
