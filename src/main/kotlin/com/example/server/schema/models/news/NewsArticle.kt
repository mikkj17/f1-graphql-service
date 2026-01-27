package com.example.server.schema.models.news

data class NewsArticle(
    val id: Int,
    val headline: String,
    val description: String,
    val lastModified: String,
    val published: String,
    val images: List<NewsImage>,
    val links: NewsLinks,
    val byline: String?,
)

data class NewsImage(
    val id: Int?,
    val type: String?,
    val name: String,
    val caption: String?,
    val alt: String?,
    val credit: String?,
    val height: Int,
    val width: Int,
    val url: String,
)

data class NewsLinks(
    val web: String?,
    val mobile: String?,
)
