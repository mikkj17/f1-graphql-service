package com.example.shared.schema.models.result

import com.example.shared.schema.models.constructor.Constructor
import com.example.shared.schema.models.driver.Driver
import com.fasterxml.jackson.annotation.JsonProperty

data class Result(
    val number: Int,
    val position: Int,
    val positionText: String,
    val points: Float,
    @JsonProperty("Driver") val driver: Driver,
    @JsonProperty("Constructor") val constructor: Constructor,
    val grid: Int,
    val laps: Int,
    val status: String,
    @JsonProperty("Time") val time: Time?,
    @JsonProperty("FastestLap") val fastestLap: FastestLap?,
)
