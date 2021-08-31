package com.app.codehero.utils

/**
FELIPE
 */

fun getTimestamp() = System.currentTimeMillis().toString()

fun addCredentialsMarvelApi(map: MutableMap<String, Any>) {
    val timestamp = getTimestamp()
    map["ts"] = timestamp
    map["apikey"] = Constants.MARVELCREDENTIALS.PUBLIC_KEY
    map["hash"] = generateHashMarvelApi(timestamp)
}

fun generateHashMarvelApi(timestamp: Any): String {
    return "$timestamp${Constants.MARVELCREDENTIALS.PRIVATE_KEY}${Constants.MARVELCREDENTIALS.PUBLIC_KEY}".toMD5()
}