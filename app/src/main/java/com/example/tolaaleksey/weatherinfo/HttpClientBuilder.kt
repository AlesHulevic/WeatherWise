package com.example.tolaaleksey.weatherinfo

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    explicitNulls = false
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = true
    coerceInputValues = true
}

fun httpClientBuilder(json: Json): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(json)
    }
    install(Logging)
    install(HttpTimeout)
    install(HttpCache)
    install(HttpRequestRetry)
    install(Auth)
    install(DataConversion)
    install(ContentEncoding)
    install(DefaultRequest)
}