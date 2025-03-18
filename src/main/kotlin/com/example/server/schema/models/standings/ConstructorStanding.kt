package com.example.server.schema.models.standings

import com.example.server.schema.models.constructor.Constructor

data class ConstructorStanding(
    val position: Int,
    val positionText: String,
    val points: Float,
    val wins: Int,
    val constructor: Constructor,
)
