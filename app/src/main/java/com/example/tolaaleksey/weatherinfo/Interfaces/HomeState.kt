package com.example.tolaaleksey.weatherinfo.Interfaces

import com.example.tolaaleksey.weatherinfo.classes.Day

sealed interface HomeState {
    data object Loading : HomeState
    data object Empty : HomeState
    data class DisplayingDays(val days: List<Day>) : HomeState
    data class Error(val e: Exception) : HomeState
}