package com.example

import com.example.di.appModule
import com.example.di.queryServiceModule
import com.example.server.schema.queryservices.*
import com.expediagroup.graphql.server.ktor.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.graphQLModule() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        anyHost() // In production, you might want to restrict this
    }

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
                inject<SprintQueryService>().value,
                inject<ScheduleQueryService>().value,
                inject<SeasonQueryService>().value,
                inject<StandingsQueryService>().value,
                inject<NewsQueryService>().value,
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
