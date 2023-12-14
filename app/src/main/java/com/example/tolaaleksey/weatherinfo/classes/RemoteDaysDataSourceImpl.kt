import android.system.Os.accept
import com.example.tolaaleksey.weatherinfo.Interfaces.RemoteDaysDataSource
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.classes.Weather
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

internal class RemoteDaysDataSourceImpl(private val client: HttpClient) : RemoteDaysDataSource {

    @Serializable
    data class Coord(
        val lon: Double,
        val lat: Double
    )

    @Serializable
    data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    @Serializable
    data class Main(
        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Int,
        val humidity: Int,
        val sea_level: Int,
        val grnd_level: Int
    )

    @Serializable
    data class Wind(
        val speed: Double,
        val deg: Int,
        val gust: Double
    )

    @Serializable
    data class Clouds(
        val all: Int
    )

    @Serializable
    data class Sys(
        val type: Int,
        val id: Int,
        val country: String,
        val sunrise: Int,
        val sunset: Int
    )

    @Serializable
    data class Example(
        val coord: Coord,
        val weather: List<Weather>,
        val base: String,
        val main: Main,
        val visibility: Int,
        val wind: Wind,
        val clouds: Clouds,
        val dt: Int,
        val sys: Sys,
        val timezone: Int,
        val id: Int,
        val name: String,
        val cod: Int
    )


    override fun getDays(): Flow<List<Day>> = flow {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${APIkey}"
        val response = client.get {
            url(url)
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer token")
        }

        val weatherData = Json.decodeFromString<Example>(String(response.readBytes()))
        emit(
            listOf(
                Day(
                    com.example.tolaaleksey.weatherinfo.classes.Weather(
                        weatherData.main.temp.toInt(),
                        weatherData.main.humidity,
                        weatherData.clouds.all,
                        0
                        //weatherData.rain.the1H.toInt()
                    ),
                    "",
                    id = UUID.randomUUID()
                )
            )
        )
    }

    internal companion object {
        const val lat = "53.90"
        const val lon = "27.56"
        const val APIkey = "5bb62da3e6d4f6a71087fdabc7e1e110"
    }
}