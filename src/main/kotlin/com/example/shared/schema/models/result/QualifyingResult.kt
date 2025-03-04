package com.example.shared.schema.models.result

import com.example.shared.schema.models.constructor.Constructor
import com.example.shared.schema.models.driver.Driver
import com.fasterxml.jackson.annotation.JsonProperty

data class QualifyingResult(
    val number: Int,
    val position: Int,
    @JsonProperty("Driver") val driver: Driver,
    @JsonProperty("Constructor") val constructor: Constructor,
    @JsonProperty("Q1") val q1: String?,
    @JsonProperty("Q2") val q2: String?,
    @JsonProperty("Q3") val q3: String?,
)
