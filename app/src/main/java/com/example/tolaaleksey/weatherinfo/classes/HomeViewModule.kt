package com.example.tolaaleksey.weatherinfo.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolaaleksey.weatherinfo.Interfaces.DayDataSource
import com.example.tolaaleksey.weatherinfo.Interfaces.HomeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

class HomeViewModule : ViewModel() {

    suspend fun onClickRemoveDay(day: Day) = DayRepositoryImpl.delete(day.id)

    val state: StateFlow<HomeState> = DayRepositoryImpl.getDays()
        .map { data ->
            when {
                data.isEmpty() -> HomeState.Empty
                else -> HomeState.DisplayingDays(data)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, HomeState.Loading)

    companion object {
        val weather = Weather(10, 10, 10, 10);

        public val DefaultDays = listOf(
            Day(weather, "Этот день крайне дождлив и печален"),
            Day(weather, "Сегодня мне не хочется делать лабу по котлину"),
            Day(weather, "Это описание братуха")
        )
    }
}