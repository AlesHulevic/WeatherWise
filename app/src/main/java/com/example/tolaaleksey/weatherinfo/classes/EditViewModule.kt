package com.example.tolaaleksey.weatherinfo.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolaaleksey.weatherinfo.Interfaces.DaysRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed interface EditState {
    data object Loading : EditState
    data class DisplayBook(val day: Day?) : EditState
}

class EditViewModule : ViewModel() {
    suspend fun onClickSave(day: Day) = DayRepositoryImpl.upsert(day)

    val state = MutableStateFlow<EditState>(EditState.Loading)
    fun setStateFlow(id: UUID?) {
        viewModelScope.launch {
            DayRepositoryImpl.getDay(id).collect { book ->
                state.value = EditState.DisplayBook(book)
            }
        }
    }
}