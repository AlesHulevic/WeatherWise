package com.example.tolaaleksey.weatherinfo.Database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tolaaleksey.weatherinfo.Database.entities.DayEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
internal interface DayDao {
    @Query("SELECT * FROM ${DayEntity.TableName}")
    fun getDays(): Flow<List<DayEntity>>

    @Query("SELECT * FROM ${DayEntity.TableName} WHERE id = :id")
    fun getDay(id: UUID): Flow<DayEntity>

    @Upsert
    suspend fun save(e: DayEntity)

    @Query("DELETE FROM ${DayEntity.TableName} WHERE id = :id")
    suspend fun delete(id: UUID)
}