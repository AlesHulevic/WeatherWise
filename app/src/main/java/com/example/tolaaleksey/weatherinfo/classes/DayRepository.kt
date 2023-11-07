package com.example.tolaaleksey.weatherinfo.classes

import com.example.tolaaleksey.weatherinfo.Interfaces.DayDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

interface DaysRepository {
    fun getDay(id: UUID?): Flow<Day?>
    fun getDays(): Flow<List<Day>>

    suspend fun upsert(day: Day)
    suspend fun delete(id: UUID)
}

object DaysRepositoryImpl : DaysRepository {

    private val dataSource: DayDataSource = HomeViewModule.InMemoryDaysDatasource

    override fun getDay(id: UUID?): Flow<Day?> {
        if (id == null) {
            return flowOf(null)
        }
        return dataSource.getDay(id)
    }

    override fun getDays(): Flow<List<Day>> {
        return dataSource.getDays()
    }

    override suspend fun upsert(day: Day) {
        dataSource.upsert(day)
    }

    override suspend fun delete(id: UUID) {
        dataSource.delete(id = id)
    }
}