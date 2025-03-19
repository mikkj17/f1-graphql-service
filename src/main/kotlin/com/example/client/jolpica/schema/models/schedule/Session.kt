package com.example.client.jolpica.schema.models.schedule

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val date: String,
    val time: String? = null,
)
