package com.example.tolaaleksey.weatherinfo.classes

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class HomeViewModule : ViewModel() {
    val days: SnapshotStateList<Day> = DefaultDays.toMutableStateList()

    fun onClickRemoveDay(day: Day) = days.remove(day)

    private companion object {
        val weather = Weather(10, 10, 10, 10);

        private val DefaultDays = listOf(
            Day(weather, "Я убивааал"),
            Day(weather, "Я насиловал"),
            Day(weather, "Это описание братуха")
        )
    }
}