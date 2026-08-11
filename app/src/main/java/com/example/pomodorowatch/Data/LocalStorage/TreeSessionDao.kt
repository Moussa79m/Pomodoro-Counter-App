package com.example.pomodorowatch.Data.LocalStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TreeSessionDao {
    @Insert
    suspend fun insertSession(session: TreeSession)
    @Query("SELECT * FROM tree_sessions ORDER BY timestamp DESc ")
    fun getAllSessions(): Flow<List<TreeSession>>

    @Query("SELECT COUNT(*) FROM tree_sessions WHERE isSuccessful=1 ")
    fun getSuccessfulSessionsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tree_sessions WHERE isSuccessful=0 ")
    fun getWitheredSessionsCount(): Flow<Int>
    @Query("SELECT SUM(durationInMinutes) FROM tree_sessions WHERE isSuccessful=1 ")
    fun getTotalFocusMinutes(): Flow<Int?>
    @Query("SELECT SUM(durationInMinutes) FROM tree_sessions ")
    fun getTotalMinutes(): Flow<Int?>
}