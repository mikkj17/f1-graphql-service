package com.example.client.espn.schema.models

import kotlinx.serialization.Serializable

@Serializable
data class EspnNewsResponse(
    val header: String,
    val articles: List<EspnArticle>
)

@Serializable
data class EspnArticle(
    val id: Int,
    val headline: String,
    val description: String,
    val lastModified: String,
    val published: String,
    val images: List<EspnImage>,
    val links: EspnLinks,
    val byline: String? = null,
)

@Serializable
data class EspnImage(
    val id: Int? = null,
    val type: String? = null,
    val name: String,
    val caption: String? = null,
    val alt: String? = null,
    val credit: String? = null,
    val height: Int,
    val width: Int,
    val url: String,
)

@Serializable
data class EspnLinks(
    val web: EspnWebLink? = null,
    val mobile: EspnMobileLink? = null,
)

@Serializable
data class EspnWebLink(
    val href: String,
)

@Serializable
data class EspnMobileLink(
    val href: String,
)
