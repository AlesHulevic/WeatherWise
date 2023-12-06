package com.example.tolaaleksey.weatherinfo.Database.Core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tolaaleksey.weatherinfo.Database.daos.DayDao
import com.example.tolaaleksey.weatherinfo.Database.entities.DayEntity

@Database(entities = [DayEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
}

internal fun AppDatabase(context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "days_database"
)
    .fallbackToDestructiveMigration()
    .build()