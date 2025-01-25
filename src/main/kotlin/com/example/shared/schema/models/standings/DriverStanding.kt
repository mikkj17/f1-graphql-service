package com.example.shared.schema.models.standings

import com.example.shared.schema.models.constructor.Constructor
import com.example.shared.schema.models.driver.Driver
import com.fasterxml.jackson.annotation.JsonProperty

data class DriverStanding(
    val position: Int,
    val positionText: String,
    val points: Float,
    val wins: Int,
    @JsonProperty("Driver") val driver: Driver,
    @JsonProperty("Constructors") val constructors: List<Constructor>,
)
