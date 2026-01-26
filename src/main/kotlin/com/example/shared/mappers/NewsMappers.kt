package com.example.shared.mappers

import com.example.client.espn.schema.models.EspnArticle
import com.example.client.espn.schema.models.EspnImage
import com.example.client.espn.schema.models.EspnLinks
import com.example.server.schema.models.news.NewsArticle
import com.example.server.schema.models.news.NewsImage
import com.example.server.schema.models.news.NewsLinks

fun EspnArticle.toNewsArticle() = NewsArticle(
    id = id,
    headline = headline,
    description = description,
    lastModified = lastModified,
    published = published,
    images = images.map { it.toNewsImage() },
    links = links.toNewsLinks(),
    byline = byline,
)

fun EspnImage.toNewsImage() = NewsImage(
    id = id,
    type = type,
    name = name,
    caption = caption,
    alt = alt,
    credit = credit,
    height = height,
    width = width,
    url = url,
)

fun EspnLinks.toNewsLinks() = NewsLinks(
    web = web?.href,
    mobile = mobile?.href,
)
