package com.example.server.utils

object Providers {
    private val driverConstructor = mapOf(
        "albon" to "williams",
        "alonso" to "aston_martin",
        "antonelli" to "mercedes",
        "bearman" to "haas",
        "bortoleto" to "audi",
        "bottas" to "cadillac",
        "colapinto" to "alpine",
        "gasly" to "alpine",
        "hadjar" to "red_bull",
        "hamilton" to "ferrari",
        "hulkenberg" to "audi",
        "lawson" to "rb",
        "leclerc" to "ferrari",
        "lindblad" to "rb",
        "norris" to "mclaren",
        "ocon" to "haas",
        "piastri" to "mclaren",
        "perez" to "cadillac",
        "russell" to "mercedes",
        "sainz" to "williams",
        "stroll" to "aston_martin",
        "max_verstappen" to "red_bull",
    )

    private val constructorColors = mapOf(
        "alpine" to "#00A1E8",
        "aston_martin" to "#229971",
        "audi" to "#FF2D00",
        "cadillac" to "#AAAAAD",
        "ferrari" to "#E8002D",
        "haas" to "#DEE1E2",
        "mclaren" to "#FF8000",
        "mercedes" to "#27F4D2",
        "rb" to "#6692FF",
        "red_bull" to "#3671C6",
        "williams" to "#1868DB",
    )

    fun currentConstructorFrom(driverId: String): String? = driverConstructor[driverId]
    fun colorFrom(constructorId: String): String? = constructorColors[constructorId]
}
