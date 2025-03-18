package com.example.shared.mappers

import com.example.server.schema.models.standings.ConstructorStanding
import com.example.server.schema.models.standings.ConstructorStandingList
import com.example.server.schema.models.standings.DriverStanding
import com.example.server.schema.models.standings.DriverStandingList
import com.example.client.jolpica.schema.models.standings.ConstructorStanding as JolpicaConstructorStanding
import com.example.client.jolpica.schema.models.standings.ConstructorStandingList as JolpicaConstructorStandingList
import com.example.client.jolpica.schema.models.standings.DriverStanding as JolpicaDriverStanding
import com.example.client.jolpica.schema.models.standings.DriverStandingList as JolpicaDriverStandingList

fun JolpicaDriverStandingList.toDriverStandingList() = DriverStandingList(
    season = season,
    round = round,
    standings = standings.map { it.toDriverStanding() },
)

fun JolpicaConstructorStandingList.toConstructorStandingList() = ConstructorStandingList(
    season = season,
    round = round,
    standings = standings.map { it.toConstructorStanding() },
)

fun JolpicaDriverStanding.toDriverStanding() = DriverStanding(
    position = position,
    positionText = positionText,
    points = points,
    wins = wins,
    driver = driver.toDriver(),
    constructors = constructors.map { it.toConstructor() },
)

fun JolpicaConstructorStanding.toConstructorStanding() = ConstructorStanding(
    position = position,
    positionText = positionText,
    points = points,
    wins = wins,
    constructor = constructor.toConstructor(),
)
