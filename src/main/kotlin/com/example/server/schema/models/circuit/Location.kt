package com.example.server.schema.models.circuit

data class Location(
    val latitude: Float,
    val longitude: Float,
    val locality: String,
    val country: String,
)
