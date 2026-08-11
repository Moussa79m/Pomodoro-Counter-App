package com.example.pomodorowatch.Repositories;

import com.example.pomodorowatch.Data.LocalStorage.TreeSession
import com.example.pomodorowatch.Data.LocalStorage.TreeSessionDao;
import kotlinx.coroutines.flow.Flow

public class TreeSessionsRepo (private val treeSessionDao:TreeSessionDao){
    suspend fun insertSession(session :TreeSession){
        treeSessionDao.insertSession(session)
    }
    val allSessions: Flow<List<TreeSession>> =treeSessionDao.getAllSessions()
    val successfulTreeCount: Flow<Int> =treeSessionDao.getSuccessfulSessionsCount()
    val witheredTreesCount:Flow<Int> =treeSessionDao.getWitheredSessionsCount()
    val totalFocusMinutes: Flow<Int?> = treeSessionDao.getTotalFocusMinutes()

    val totalMinutes: Flow<Int?> = treeSessionDao.getTotalMinutes()

}
