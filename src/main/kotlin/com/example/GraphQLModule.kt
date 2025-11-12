package com.example

import com.example.di.appModule
import com.example.di.queryServiceModule
import com.example.server.schema.queryservices.*
import com.expediagroup.graphql.server.ktor.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.graphQLModule() {
    // Initialize Koin
    install(Koin) {
        slf4jLogger()
        modules(appModule, queryServiceModule)
    }

    install(StatusPages) {
        defaultGraphQLStatusPages()
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
        graphQLGetRoute()
        graphQLPostRoute()
        graphiQLRoute()
        graphQLSDLRoute()
    }
}
