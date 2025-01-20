package com.example.client.schema.models

import com.example.server.schema.models.Location

data class Location(
    val lat: Double,
    val long: Double,
    val locality: String,
    val country: String,
) {
    fun toLocation() = Location(
        latitude = lat,
        longitude = long,
        locality = locality,
        country = country,
    )
}
