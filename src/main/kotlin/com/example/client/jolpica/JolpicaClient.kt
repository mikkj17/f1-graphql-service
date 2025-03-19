package com.example.client.jolpica

import com.example.client.jolpica.schema.ApiResponse
import com.example.client.jolpica.schema.MrData
import com.example.client.jolpica.schema.models.JolpicaModel
import com.example.client.jolpica.schema.models.circuit.Circuit
import com.example.client.jolpica.schema.models.constructor.Constructor
import com.example.client.jolpica.schema.models.driver.Driver
import com.example.client.jolpica.schema.models.lap.RaceLaps
import com.example.client.jolpica.schema.models.pitstop.RacePitStop
import com.example.client.jolpica.schema.models.result.Qualifying
import com.example.client.jolpica.schema.models.result.Race
import com.example.client.jolpica.schema.models.schedule.Schedule
import com.example.client.jolpica.schema.models.season.Season
import com.example.client.jolpica.schema.models.standings.ConstructorStandingList
import com.example.client.jolpica.schema.models.standings.DriverStandingList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

typealias ModelsExtractor<T> = MrData<T>.() -> List<T>

class JolpicaClient {
    private val host = "api.jolpi.ca"
    private val path = "/ergast/f1"
    private val limit = 100

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.NONE
        }
    }

    private suspend inline fun <reified T : JolpicaModel> fetchChunk(
        endpoint: String,
        offset: Int,
        pathParameters: Array<String>,
        params: Array<String>,
    ): ApiResponse<T> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = this@JolpicaClient.host
            path(path, *pathParameters, endpoint, *params)
            parameters.append("limit", limit.toString())
            parameters.append("offset", offset.toString())
        }

        return client.get(url.buildString()).body()
    }

    private suspend inline fun <reified T : JolpicaModel> fetch(
        endpoint: String,
        pathParameters: Array<String> = emptyArray(),
        params: Array<String> = emptyArray(),
        crossinline getModels: ModelsExtractor<T>,
    ): T {
        val response: ApiResponse<T> = fetchChunk(endpoint, 0, pathParameters, params)
        return response
            .data
            .getModels()
            .first()
    }

    private suspend inline fun <reified T : JolpicaModel> fetchAll(
        endpoint: String,
        pathParameters: Array<String> = emptyArray(),
        params: Array<String> = emptyArray(),
        crossinline getModels: ModelsExtractor<T>,
    ): List<T> {
        // Fetch the first chunk and total records
        val response: ApiResponse<T> = fetchChunk(endpoint, 0, pathParameters, params)
        val firstChunk = response.data.getModels()

        // Calculate offsets for the remaining chunks
        val offsets = (limit until response.data.total step limit)

        // Fetch remaining chunks in parallel
        val remainingChunks = coroutineScope {
            offsets.chunked(5).map { batch ->   // fetch 5 chunks at a time to avoid hitting the rate limit
                async(Dispatchers.IO) {
                    batch.map { offset ->
                        fetchChunk<T>(endpoint, offset, pathParameters, params).data.getModels()
                    }.flatten()
                }
            }.awaitAll()
        }

        // Combine all chunks
        return firstChunk + remainingChunks.flatten()
    }

    suspend fun getDrivers(year: Int?, round: Int?) = fetchAll<Driver>(
        "drivers",
        pathParameters = listOfNotNull(year?.toString(), round?.toString()).toTypedArray(),
    ) {
        driverTable!!.drivers!!
    }

    suspend fun getDriversByConstructor(constructorId: String) = fetchAll<Driver>(
        "drivers",
        pathParameters = arrayOf("constructors", constructorId),
    ) {
        driverTable!!.drivers!!
    }

    suspend fun getDriver(driverId: String) = fetch<Driver>("drivers", params = arrayOf(driverId)) {
        driverTable!!.drivers!!
    }

    suspend fun getConstructors(year: Int?, round: Int?) = fetchAll<Constructor>(
        "constructors",
        pathParameters = listOfNotNull(year?.toString(), round?.toString()).toTypedArray(),
    ) {
        constructorTable!!.constructors!!
    }

    suspend fun getConstructorsByDriver(driverId: String) = fetchAll<Constructor>(
        "constructors",
        pathParameters = arrayOf("drivers", driverId),
    ) {
        constructorTable!!.constructors!!
    }

    suspend fun getCircuits(year: Int?, round: Int?) = fetchAll<Circuit>(
        "circuits",
        pathParameters = listOfNotNull(year?.toString(), round?.toString()).toTypedArray(),
    ) {
        circuitTable!!.circuits!!
    }

    suspend fun getSeasons() = fetchAll<Season>("seasons") {
        seasonTable!!.seasons!!
    }

    suspend fun getRaces(year: Int, round: Int?) = fetchAll<Race>(
        "results",
        pathParameters = listOfNotNull(year.toString(), round?.toString()).toTypedArray(),
    ) {
        raceTable!!.races!!
    }

    suspend fun getRaceByDriver(driverId: String) = fetchAll<Race>(
        "drivers",
        params = arrayOf(driverId, "results"),
    ) {
        raceTable!!.races!!
    }

    suspend fun getRaceByConstructor(constructorId: String) = fetchAll<Race>(
        "constructors",
        params = arrayOf(constructorId, "results"),
    ) {
        raceTable!!.races!!
    }

    suspend fun getQualifying(year: Int, round: Int) = fetch<Qualifying>(
        "qualifying",
        pathParameters = arrayOf(year.toString(), round.toString()),
    ) {
        raceTable!!.races!!
    }

    suspend fun getSchedules(year: Int) = fetchAll<Schedule>(
        "races",
        pathParameters = arrayOf(year.toString()),
    ) {
        raceTable!!.races!!
    }

    suspend fun getSchedulesByDriver(driverId: String) = fetchAll<Schedule>(
        "drivers",
        params = arrayOf(driverId, "races"),
    ) {
        raceTable!!.races!!
    }

    suspend fun getSchedulesByConstructor(constructorId: String) = fetchAll<Schedule>(
        "constructors",
        params = arrayOf(constructorId, "races"),
    ) {
        raceTable!!.races!!
    }

    suspend fun getDriverStandings(year: Int, round: Int?) = fetch<DriverStandingList>(
        "driverStandings",
        pathParameters = listOfNotNull(year.toString(), round?.toString()).toTypedArray(),
    ) {
        standingsTable!!.standingsLists!!
    }

    suspend fun getConstructorStandings(year: Int, round: Int?) = fetch<ConstructorStandingList>(
        "constructorStandings",
        pathParameters = listOfNotNull(year.toString(), round?.toString()).toTypedArray(),
    ) {
        standingsTable!!.standingsLists!!
    }

    suspend fun getLaps(year: Int, round: Int) = fetchAll<RaceLaps>(
        "laps",
        pathParameters = arrayOf(year.toString(), round.toString()),
    ) {
        raceTable!!.races!!
    }

    suspend fun getPitStops(year: Int, round: Int) = fetch<RacePitStop>(
        "pitstops",
        pathParameters = arrayOf(year.toString(), round.toString()),
    ) {
        raceTable!!.races!!
    }
}
