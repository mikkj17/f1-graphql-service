package com.example

import com.example.server.schema.queryservices.*
import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphQLSDLRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(GraphQL) {
        schema {
            packages = listOf("com.example")
            queries = listOf(
                DriverQueryService(),
                ConstructorQueryService(),
                CircuitQueryService(),
                SeasonQueryService(),
                RaceQueryService(),
                QualifyingQueryService(),
                ScheduleQueryService(),
                StandingsQueryService(),
                LapQueryService(),
                PitStopQueryService(),
            )
        }
    }
    routing {
        graphQLPostRoute()
        graphiQLRoute()
        graphQLSDLRoute()
    }
}
