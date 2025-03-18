package com.example.client.jolpica.schema.models.season

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Season(
    @SerialName("season") val year: Int,
    val url: String,
) : JolpicaModel
