package com.example.server.schema.models.standings

import com.example.server.schema.models.Model

data class ConstructorStandingList(
    val season: Int,
    val round: Int,
    val standings: List<ConstructorStanding>,
) : Model
