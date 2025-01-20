package com.example.server.schema.models

data class Circuit(
    val id: String,
    val url: String,
    val name: String,
    val location: Location,
) : Model
