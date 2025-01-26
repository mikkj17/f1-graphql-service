package com.example.shared.schema.models.constructor

import com.example.client.schema.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class Constructor(
    @JsonProperty("constructorId") val id: String,
    val url: String,
    val name: String,
    val nationality: String,
) : Model
