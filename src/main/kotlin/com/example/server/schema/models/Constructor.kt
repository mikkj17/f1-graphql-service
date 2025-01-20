package com.example.server.schema.models

data class Constructor(
    val id: String,
    val url: String,
    val name: String,
    val nationality: String,
) : Model
