package com.example.pomodorowatch.Data.LocalStorage

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tree_sessions")
data class TreeSession (
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    val durationInMinutes: Int,
    val isSuccessful: Boolean,
    val treeString: Int,
    val timeStamp: Long = System.currentTimeMillis())