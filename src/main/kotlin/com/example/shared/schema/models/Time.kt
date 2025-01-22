package com.example.shared.schema.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Time(
    @JsonProperty("millis") val milliseconds: Int,
    val time: String,
)
