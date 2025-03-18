package com.example.shared.mappers

import com.example.server.schema.models.season.Season
import com.example.client.jolpica.schema.models.season.Season as JolpicaSeason

fun JolpicaSeason.toSeason() = Season(
    year = year,
    url = url,
)
