package com.example.shared.schema.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Season(
    @JsonProperty("season") val year: String,
    val url: String,
) : Model
