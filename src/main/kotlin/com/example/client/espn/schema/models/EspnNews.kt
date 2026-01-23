package com.example.client.espn.schema.models

import kotlinx.serialization.Serializable

@Serializable
data class EspnNewsResponse(
    val header: String,
    val articles: List<EspnArticle>
)

@Serializable
data class EspnArticle(
    val id: Long,
    val headline: String,
    val description: String,
    val lastModified: String,
    val published: String,
    val images: List<EspnImage>,
    val links: EspnLinks? = null
)

@Serializable
data class EspnImage(
    val id: Long,
    val url: String,
    val caption: String? = null,
    val credit: String? = null
)

@Serializable
data class EspnLinks(
    val web: EspnWebLink? = null,
    val mobile: EspnMobileLink? = null
)

@Serializable
data class EspnWebLink(
    val href: String
)

@Serializable
data class EspnMobileLink(
    val href: String
)
