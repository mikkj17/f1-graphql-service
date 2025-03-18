package com.example.server.schema.models.circuit

import com.example.server.schema.models.Model

data class Circuit(
    val id: String,
    val url: String,
    val name: String,
    val location: Location,
) : Model
