package com.example.shared.mappers

import com.example.server.schema.models.schedule.Schedule
import com.example.server.schema.models.schedule.Session
import com.example.client.jolpica.schema.models.schedule.Schedule as JolpicaSchedule
import com.example.client.jolpica.schema.models.schedule.Session as JolpicaSession

fun JolpicaSchedule.toSchedule() = Schedule(
    season = season,
    round = round,
    url = url,
    raceName = raceName,
    circuit = circuit.toCircuit(),
    date = date,
    time = time,
    firstPractice = firstPractice?.toSession(),
    secondPractice = secondPractice?.toSession(),
    thirdPractice = thirdPractice?.toSession(),
    qualifying = qualifying?.toSession(),
    sprintQualifying = sprintQualifying?.toSession(),
    sprint = sprint?.toSession(),
)

fun JolpicaSession.toSession() = Session(
    date = date,
    time = time,
)
