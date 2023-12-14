package com.example.tolaaleksey.weatherinfo.classes

import com.example.tolaaleksey.weatherinfo.Database.daos.DayDao
import com.example.tolaaleksey.weatherinfo.Database.entities.DayEntity
import com.example.tolaaleksey.weatherinfo.Interfaces.DayDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

internal class DaysDatasourceImpl(private val dao: DayDao) : DayDataSource {

    override fun getDays(): Flow<List<Day>> {
        return dao.getDays().map {
            it.map { dayEntity ->
                transformToDay(dayEntity)
            }
        }
    }

    override fun getDay(id: UUID): Flow<Day?> {
        return dao.getDay(id).map { dayEntity ->
            transformToDay(dayEntity)
        }
    }

    override suspend fun upsert(day: Day) {
        dao.save(transformToDayEntity(day))
    }

    override suspend fun delete(id: UUID) {
        dao.delete(id)
    }
}

fun transformToDay(dayEntity: DayEntity?): Day {
    return if (dayEntity != null) Day(
        Weather(
            dayEntity.temperature,
            dayEntity.humidity,
            dayEntity.cloudiness,
            dayEntity.chanceOfRain
        ),
        dayEntity.description,
        dayEntity.id
    ) else Day(Weather(0, 0, 0, 0), "");
}

fun transformToDayEntity(day: Day): DayEntity {
    return DayEntity(
        day.id,
        day.weather.temperature,
        day.weather.humidity,
        day.weather.cloudiness,
        day.weather.chanceOfRain,
        day.description
    )
}