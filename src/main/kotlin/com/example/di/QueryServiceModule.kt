package com.example.di

import com.example.server.schema.queryservices.*
import org.koin.dsl.module

val queryServiceModule = module {
    // Query Services
    single { CircuitQueryService(get()) }
    single { ConstructorQueryService(get()) }
    single { DriverQueryService(get()) }
    single { LapQueryService(get()) }
    single { PitStopQueryService(get()) }
    single { QualifyingQueryService(get()) }
    single { RaceQueryService(get()) }
    single { SprintQueryService(get()) }
    single { ScheduleQueryService(get()) }
    single { SeasonQueryService(get()) }
    single { StandingsQueryService(get()) }
}
