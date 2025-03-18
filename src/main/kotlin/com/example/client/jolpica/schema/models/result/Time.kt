package com.example.client.jolpica.schema.models.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Time(
    @SerialName("millis") val milliseconds: Int,
    val time: String,
)
