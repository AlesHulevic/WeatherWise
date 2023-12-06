package com.example.tolaaleksey.weatherinfo.classes

import android.os.Parcel
import android.os.Parcelable
import com.example.tolaaleksey.weatherinfo.Interfaces.RemoteDaysDataSource
import kotlinx.coroutines.flow.Flow

internal class RemoteDaysRepositoryImpl(private val remoteDaysDataSource: RemoteDaysDataSource) :
    RemoteDaysRepository {
    override fun getDays(): Flow<List<Day>> {
        return remoteDaysDataSource.getDays()
    }


}