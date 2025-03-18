package com.example.shared.mappers

import com.example.client.jolpica.schema.models.circuit.Location
import com.example.server.schema.models.circuit.Circuit
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit

fun JolpicaCircuit.toCircuit() = Circuit(
    id = id,
    url = url,
    name = name,
    location = location.toLocation(),
)

fun Location.toLocation() = com.example.server.schema.models.circuit.Location(
    latitude = latitude,
    longitude = longitude,
    locality = locality,
    country = country,
)
