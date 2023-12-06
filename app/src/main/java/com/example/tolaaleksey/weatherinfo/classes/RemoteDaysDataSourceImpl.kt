import android.system.Os.accept
import com.example.tolaaleksey.weatherinfo.Interfaces.RemoteDaysDataSource
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.classes.Weather

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

internal class RemoteDaysDataSourceImpl(private val client: HttpClient) : RemoteDaysDataSource {

    data class WeatherData(
        val city: City,

        val cod: String,
        val message: Double,
        val cnt: Int,

        val list: List<Weather>
    )

    data class City(
        val id: Int,
        val name: String,
        val coord: Coord,
        val country: String,
        val population: Int,
        val timezone: Int
    )

    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Weather(
        val dt: Long,
        val sunrise: Long,
        val sunset: Long,
        val temp: Temperature,
        val feels_like: Temperature,
        val pressure: Int,
        val humidity: Int,
        val weather: List<WeatherInfo>,
        val speed: Double,
        val deg: Int,
        val gust: Double,
        val clouds: Int,
        val pop: Double,
        val rain: Double
    )

    data class Temperature(
        val day: Double,
        val min: Double,
        val max: Double,
        val night: Double,
        val eve: Double,
        val morn: Double
    )

    data class WeatherInfo(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    override fun getDays(): Flow<List<Day>> = flow {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${APIkey}"
        val response = client.get {
            url(url)
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer token")
        }
        val weatherData = Json.decodeFromString<WeatherData>(String(response.readBytes()))
        emit(weatherData.list.map {
            Day(
                Weather(
                    it.clouds,
                    it.humidity,
                    it.clouds,
                    it.rain.toInt()
                ),
                "",
                id = UUID.randomUUID()
            )
        }
        )
    }

    internal companion object {
        const val lat = "53.90"
        const val lon = "27.56"
        const val APIkey = "5bb62da3e6d4f6a71087fdabc7e1e110"
    }
}