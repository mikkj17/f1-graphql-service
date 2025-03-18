package com.example.client.jolpica.schema.models.driver

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    @SerialName("driverId") val id: String,
    @SerialName("permanentNumber") val number: Int? = null,
    val code: String? = null,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String,
) : JolpicaModel
