package com.example.tolaaleksey.weatherinfo.classes

import com.example.tolaaleksey.weatherinfo.Interfaces.DayDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.UUID

object InMemoryDaysDatasource : DayDataSource {

    private val days = HomeViewModule.DefaultDays.associateBy { it.id }.toMutableMap()

    private val _dayFlow = MutableSharedFlow<Map<UUID, Day>>(1)

    override suspend fun upsert(day: Day) {
        days[day.id] = day
        _dayFlow.emit(days)
    }

    override suspend fun delete(id: UUID) {
        days.remove(id)
        _dayFlow.emit(days)
    }

    override fun getDays(): Flow<List<Day>> {
        return _dayFlow
            .asSharedFlow()
            .map { it.values.toList() }
            .onStart {
                delay(1000L)
                emit(days.values.toList())
            }
    }

    override fun getDay(id: UUID): Flow<Day?> {
        return _dayFlow
            .asSharedFlow()
            .map { it[id] }
            .onStart {
                delay(1000L)
                emit(days[id])
            }
    }
}