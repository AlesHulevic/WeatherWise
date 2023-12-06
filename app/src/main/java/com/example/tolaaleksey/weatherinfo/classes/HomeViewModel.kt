package com.example.tolaaleksey.weatherinfo.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolaaleksey.weatherinfo.Interfaces.DaysRepository
import com.example.tolaaleksey.weatherinfo.Interfaces.HomeState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val daysRepository: DaysRepository) : ViewModel() {
    suspend fun onClickRemoveDay(day: Day) = daysRepository.delete(day.id)

    val state: StateFlow<HomeState> = daysRepository.getDays()
        .map { data ->
            when {
                data.isEmpty() -> HomeState.Empty
                else -> HomeState.DisplayingDays(data)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, HomeState.Loading)
}