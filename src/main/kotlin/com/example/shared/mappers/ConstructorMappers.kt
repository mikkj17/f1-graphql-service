package com.example.shared.mappers

import com.example.server.schema.models.constructor.Constructor
import com.example.server.schema.utils.Providers
import com.example.client.jolpica.schema.models.constructor.Constructor as JolpicaConstructor

fun JolpicaConstructor.toConstructor() = Constructor(
    id = id,
    url = url,
    nationality = nationality,
    name = name,
    color = Providers.colorFrom(id)
)
