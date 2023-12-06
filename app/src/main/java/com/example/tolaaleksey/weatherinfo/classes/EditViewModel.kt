package com.example.tolaaleksey.weatherinfo.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolaaleksey.weatherinfo.Interfaces.DaysRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed interface EditState {
    data object Loading : EditState
    data class DisplayDay(val day: Day?) : EditState
}

internal class EditViewModule(private val daysRepository: DaysRepository) : ViewModel() {
    suspend fun onClickSave(day: Day) = daysRepository.upsert(day)

    val state = MutableStateFlow<EditState>(EditState.Loading)
    fun setStateFlow(id: UUID?) {
        viewModelScope.launch {
            daysRepository.getDay(id).collect { day ->
                state.value = EditState.DisplayDay(day)
            }
        }
    }
}