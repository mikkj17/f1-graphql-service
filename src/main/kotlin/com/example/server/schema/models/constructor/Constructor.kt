package com.example.server.schema.models.constructor

import com.example.server.schema.models.Model

data class Constructor(
    val id: String,
    val url: String,
    val name: String,
    val nationality: String,
) : Model
