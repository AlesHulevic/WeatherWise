package com.example.tolaaleksey.weatherinfo.Interfaces

import com.example.tolaaleksey.weatherinfo.classes.Day
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DayDataSource {
    fun getDays(): Flow<List<Day>>
    fun getDay(id: UUID): Flow<Day?>

    suspend fun upsert(day: Day)
    suspend fun delete(id: UUID)
}