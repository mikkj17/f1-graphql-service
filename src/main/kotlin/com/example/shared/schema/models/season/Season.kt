package com.example.shared.schema.models.season

import com.example.shared.schema.models.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class Season(
    @JsonProperty("season") val year: Int,
    val url: String,
) : Model
