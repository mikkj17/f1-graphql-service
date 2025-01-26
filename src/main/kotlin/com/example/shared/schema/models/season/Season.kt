package com.example.shared.schema.models.season

import com.example.client.schema.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class Season(
    @JsonProperty("season") val year: Int,
    val url: String,
) : Model
