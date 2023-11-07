package com.example.tolaaleksey.weatherinfo.classes

import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tolaaleksey.weatherinfo.Interfaces.DayDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

class HomeViewModule : ViewModel() {

    suspend fun onClickRemoveDay(day: Day) = DaysRepositoryImpl.delete(day.id)
    suspend fun onClickAddDay(day: Day) = DaysRepositoryImpl.upsert(day)

    val state: StateFlow<HomeState> = DaysRepositoryImpl.getDays()
        .map { data ->
            when {
                data.isEmpty() -> HomeState.Empty
                else -> HomeState.DisplayingDays(data)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, HomeState.Loading)

    private companion object {
        val weather = Weather(10, 10, 10, 10);

        private val DefaultDays = listOf(
            Day(weather, "Этот день крайне дождлив и печален"),
            Day(weather, "Сегодня мне не хочется делать лабу по котлину"),
            Day(weather, "Это описание братуха")
        )
    }

    object InMemoryDaysDatasource : DayDataSource {

        private val days = DefaultDays.associateBy { it.id }.toMutableMap()

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
}


sealed interface HomeState {
    data object Loading : HomeState
    data object Empty : HomeState
    data class DisplayingDays(val days: List<Day>) : HomeState
    data class Error(val e: Exception) : HomeState
}