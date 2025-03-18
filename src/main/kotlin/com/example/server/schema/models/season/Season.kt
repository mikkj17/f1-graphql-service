package com.example.server.schema.models.season

import com.example.server.schema.models.Model

data class Season(
    val year: Int,
    val url: String,
) : Model
