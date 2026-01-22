package com.example.client.jolpica

import com.example.client.jolpica.schema.MrData
import com.example.client.jolpica.schema.models.circuit.Circuit
import com.example.client.jolpica.schema.models.constructor.Constructor
import com.example.client.jolpica.schema.models.driver.Driver
import com.example.client.jolpica.schema.models.lap.RaceLaps
import com.example.client.jolpica.schema.models.pitstop.RacePitStop
import com.example.client.jolpica.schema.models.result.Qualifying
import com.example.client.jolpica.schema.models.result.Race
import com.example.client.jolpica.schema.models.result.Sprint
import com.example.client.jolpica.schema.models.schedule.Schedule
import com.example.client.jolpica.schema.models.season.Season
import com.example.client.jolpica.schema.models.standings.ConstructorStandingList
import com.example.client.jolpica.schema.models.standings.DriverStandingList

typealias ModelsExtractor<T> = MrData<T>.() -> List<T>

interface IJolpicaClient {
    suspend fun getDrivers(year: Int?, round: Int?): List<Driver>
    suspend fun getDriversByConstructor(constructorId: String): List<Driver>
    suspend fun getDriver(driverId: String): Driver?
    suspend fun getConstructors(year: Int?, round: Int?): List<Constructor>
    suspend fun getConstructorsByDriver(driverId: String): List<Constructor>
    suspend fun getCircuits(year: Int?, round: Int?): List<Circuit>
    suspend fun getSeasons(): List<Season>
    suspend fun getRaces(year: Int, round: Int?): List<Race>
    suspend fun getRacesByDriver(driverId: String): List<Race>
    suspend fun getRacesByConstructor(constructorId: String): List<Race>
    suspend fun getRacesByCircuit(circuitId: String): List<Race>
    suspend fun getMostRecentRace(): Race?
    suspend fun getSprints(year: Int, round: Int?): List<Sprint>
    suspend fun getQualifyings(year: Int, round: Int?): List<Qualifying>
    suspend fun getQualifyingsByDriver(driverId: String): List<Qualifying>
    suspend fun getQualifyingsByConstructor(constructorId: String): List<Qualifying>
    suspend fun getQualifyingsByCircuit(circuitId: String): List<Qualifying>
    suspend fun getSchedules(year: Int, round: Int?): List<Schedule>
    suspend fun getSchedulesByDriver(driverId: String): List<Schedule>
    suspend fun getSchedulesByConstructor(constructorId: String): List<Schedule>
    suspend fun getDriverStandings(year: Int, round: Int?): DriverStandingList?
    suspend fun getConstructorStandings(year: Int, round: Int?): ConstructorStandingList?
    suspend fun getLaps(year: Int, round: Int): List<RaceLaps>
    suspend fun getPitStops(year: Int, round: Int): RacePitStop?
}
