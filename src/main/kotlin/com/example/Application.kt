package com.example

import com.example.di.appModule
import com.example.di.queryServiceModule
import com.example.server.schema.queryservices.*
import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphQLSDLRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // Initialize Koin
    install(Koin) {
        slf4jLogger()
        modules(appModule, queryServiceModule)
    }

    install(GraphQL) {
        schema {
            packages = listOf("com.example")
            queries = listOf(
                inject<CircuitQueryService>().value,
                inject<ConstructorQueryService>().value,
                inject<DriverQueryService>().value,
                inject<LapQueryService>().value,
                inject<PitStopQueryService>().value,
                inject<QualifyingQueryService>().value,
                inject<RaceQueryService>().value,
                inject<ScheduleQueryService>().value,
                inject<SeasonQueryService>().value,
                inject<StandingsQueryService>().value,
            )
        }
    }
    routing {
        graphQLPostRoute()
        graphiQLRoute()
        graphQLSDLRoute()
    }
}
