package com.example.shared.schema.models.driver

import com.example.client.schema.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class Driver(
    @JsonProperty("driverId") val id: String,
    @JsonProperty("permanentNumber") val number: Int,
    val code: String?,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String,
) : Model
