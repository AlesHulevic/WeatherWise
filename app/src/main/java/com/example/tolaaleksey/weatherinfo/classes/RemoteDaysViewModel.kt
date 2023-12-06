package com.example.tolaaleksey.weatherinfo.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolaaleksey.weatherinfo.Interfaces.DaysRepository
import com.example.tolaaleksey.weatherinfo.Interfaces.HomeState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface RemoteDaysState {
    data object Loading : RemoteDaysState
    data class DisplayingDays(val days: List<Day>) : RemoteDaysState
}

internal class RemoteDaysViewModel(
    private val remoteDaysRepository: RemoteDaysRepository,
    private val daysRepository: DaysRepository
) :
    ViewModel() {

    suspend fun onClickAddDay(day: Day) = daysRepository.upsert(day)

    val state: StateFlow<RemoteDaysState> = remoteDaysRepository.getDays()
        .map { data ->
            RemoteDaysState.DisplayingDays(data)
        }.stateIn(viewModelScope, SharingStarted.Lazily, RemoteDaysState.Loading)
}