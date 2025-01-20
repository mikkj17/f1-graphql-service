package com.example.server.schema.models

data class Location(
    val latitude: Double,
    val longitude: Double,
    val locality: String,
    val country: String,
) : Model
