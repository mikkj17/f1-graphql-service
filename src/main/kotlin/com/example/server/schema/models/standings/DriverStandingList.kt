package com.example.server.schema.models.standings

import com.example.server.schema.models.Model

data class DriverStandingList(
    val season: Int,
    val round: Int,
    val standings: List<DriverStanding>,
) : Model
