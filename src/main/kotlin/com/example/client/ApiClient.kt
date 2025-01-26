package com.example.client

import com.example.client.schema.ApiResponse
import com.example.client.schema.Model
import com.example.client.schema.MrData
import com.example.client.schema.models.lap.RaceLaps
import com.example.shared.schema.models.circuit.Circuit
import com.example.shared.schema.models.constructor.Constructor
import com.example.shared.schema.models.driver.Driver
import com.example.shared.schema.models.pitStop.RacePitStop
import com.example.shared.schema.models.result.Qualifying
import com.example.shared.schema.models.result.Race
import com.example.shared.schema.models.schedule.Schedule
import com.example.shared.schema.models.season.Season
import com.example.shared.schema.models.standings.ConstructorStandingList
import com.example.shared.schema.models.standings.DriverStandingList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

typealias ModelsExtractor<T> = MrData<T>.() -> List<T>

class ApiClient {
    private val host = "api.jolpi.ca"
    private val path = "/ergast/f1"
    private val limit = 100

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    private suspend inline fun <reified T : Model> fetchChunk(
        endpoint: String,
        offset: Int,
        pathParameters: Array<String>,
        params: Array<String>,
    ): ApiResponse<T> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = this@ApiClient.host
            path(path, *pathParameters, endpoint, *params)
            parameters.append("limit", limit.toString())
            parameters.append("offset", offset.toString())
        }

        return client.get(url.buildString()).body()
    }

    private suspend inline fun <reified T : Model> fetch(
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

    private suspend inline fun <reified T : Model> fetchAll(
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
            offsets.map { offset ->
                async(Dispatchers.IO) {
                    val chunk: ApiResponse<T> = fetchChunk(endpoint, offset, pathParameters, params)
                    chunk.data.getModels()
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

    suspend fun getDriver(driverId: String) = fetch<Driver>("drivers", params = arrayOf(driverId)) {
        driverTable!!.drivers!!
    }

    suspend fun getConstructors(year: Int?, round: Int?) = fetchAll<Constructor>(
        "constructors",
        pathParameters = listOfNotNull(year?.toString(), round?.toString()).toTypedArray(),
    ) {
        constructorTable!!.constructors!!
    }

    suspend fun getConstructor(constructorId: String) = fetch<Constructor>(
        "constructors",
        params = arrayOf(constructorId),
    ) {
        constructorTable!!.constructors!!
    }

    suspend fun getCircuits(year: Int?, round: Int?) = fetchAll<Circuit>(
        "circuits",
        pathParameters = listOfNotNull(year?.toString(), round?.toString()).toTypedArray(),
    ) {
        circuitTable!!.circuits!!
    }

    suspend fun getCircuit(circuitId: String) = fetch<Circuit>("circuits", params = arrayOf(circuitId)) {
        circuitTable!!.circuits!!
    }

    suspend fun getSeasons() = fetchAll<Season>("seasons") {
        seasonTable!!.seasons!!
    }

    suspend fun getRace(year: Int, round: Int) = fetch<Race>(
        "results",
        pathParameters = arrayOf(year.toString(), round.toString()),
    ) {
        raceTable!!.races!!
    }

    suspend fun getQualifying(year: Int, round: Int) = fetch<Qualifying>(
        "qualifying",
        pathParameters = arrayOf(year.toString(), round.toString()),
    ) {
        raceTable!!.races!!
    }

    suspend fun getSchedules(year: Int) = fetchAll<Schedule>("", pathParameters = arrayOf(year.toString())) {
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
