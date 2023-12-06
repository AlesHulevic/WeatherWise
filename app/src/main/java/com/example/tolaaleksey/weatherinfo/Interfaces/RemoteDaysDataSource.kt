package com.example.tolaaleksey.weatherinfo.Interfaces

import com.example.tolaaleksey.weatherinfo.classes.Day
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteDaysDataSource {
    fun getDays(): Flow<List<Day>>
}