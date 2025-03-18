package com.example.client.jolpica.schema.models.result

import com.example.client.jolpica.schema.models.constructor.Constructor
import com.example.client.jolpica.schema.models.driver.Driver
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QualifyingResult(
    val number: Int,
    val position: Int,
    @SerialName("Driver") val driver: Driver,
    @SerialName("Constructor") val constructor: Constructor,
    @SerialName("Q1") val q1: String? = null,
    @SerialName("Q2") val q2: String? = null,
    @SerialName("Q3") val q3: String? = null,
)
