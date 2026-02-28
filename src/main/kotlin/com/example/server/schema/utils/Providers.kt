package com.example.server.schema.utils

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
        "audi" to "#4d4742",
        "cadillac" to "#B2C2D2",
        "ferrari" to "#ED1131",
        "haas" to "#9C9FA2",
        "mclaren" to "#F47600",
        "mercedes" to "#00D7B6",
        "rb" to "#6C98FF",
        "red_bull" to "#4781D7",
        "williams" to "#1868DB",
    )

    fun currentConstructorFrom(driverId: String): String? = driverConstructor[driverId]
    fun colorFrom(constructorId: String): String? = constructorColors[constructorId]
}
