package com.example.timetable.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface SlotDao {
    @Upsert
    suspend fun insert(slot: DataOfSlot)
    @Query("SELECT * FROM Slot")
    fun getAll(): Flow<List<DataOfSlot>>
    @Query("SELECT * FROM Slot WHERE courseId = :courseId")
    fun findByCourseId(courseId: String): Flow<DataOfSlot>
    @Delete
    suspend fun delete(slot: DataOfSlot)
    @Query("DELETE FROM Slot WHERE courseId = :courseId")
    suspend fun deleteByCourseId(courseId: String)
    @Query("DELETE FROM Slot")
    suspend fun deleteAll()

}