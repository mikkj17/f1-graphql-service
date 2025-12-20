package com.example.client.jolpica.schema.models.constructor

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Constructor(
    @SerialName("constructorId") val id: String,
    val url: String? = null,
    val name: String,
    val nationality: String,
) : JolpicaModel
