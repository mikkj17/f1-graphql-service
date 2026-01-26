package com.example.di

import com.example.client.espn.EspnClient
import com.example.client.espn.IEspnClient
import com.example.client.jolpica.IJolpicaClient
import com.example.client.jolpica.JolpicaClient
import com.example.client.openf1.OpenF1Client
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    // HTTP Client for Jolpica
    single(qualifier = named("jolpica-client")) {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.NONE
            }
        }
    }

    // HTTP Client for OpenF1
    @OptIn(ExperimentalSerializationApi::class)
    single(qualifier = named("openf1-client")) {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    namingStrategy = JsonNamingStrategy.SnakeCase
                })
            }
            install(Logging) {
                level = LogLevel.NONE
            }
        }
    }

    // HTTP Client for ESPN
    single(qualifier = named("espn-client")) {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.NONE
            }
        }
    }

    // API Clients
    single<IJolpicaClient> { JolpicaClient(get(qualifier = named("jolpica-client"))) }
    single { OpenF1Client(get(qualifier = named("openf1-client"))) }
    single<IEspnClient> { EspnClient(get(qualifier = named("espn-client"))) }
}
