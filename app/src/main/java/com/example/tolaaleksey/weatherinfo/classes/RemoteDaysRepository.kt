package com.example.tolaaleksey.weatherinfo.classes

import kotlinx.coroutines.flow.Flow

interface RemoteDaysRepository {
    fun getDays(): Flow<List<Day>>;
}