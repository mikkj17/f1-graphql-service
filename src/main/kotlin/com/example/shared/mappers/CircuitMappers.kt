package com.example.shared.mappers

import com.example.server.schema.models.circuit.Circuit
import com.example.server.schema.models.circuit.Location
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation

fun JolpicaCircuit.toCircuit() = Circuit(
    id = id,
    url = url,
    name = name,
    location = location.toLocation(),
)

fun JolpicaLocation.toLocation() = Location(
    latitude = latitude,
    longitude = longitude,
    locality = locality,
    country = country,
)
