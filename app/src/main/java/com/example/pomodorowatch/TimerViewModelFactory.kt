package com.example.pomodorowatch

import TimerViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pomodorowatch.Repositories.TreeSessionsRepo
//import com.example.pomodorowatch.ViewModel.TimerViewModel

class TimerViewModelFactory(private val repo: TreeSessionsRepo): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass:Class<T>):T{
if(modelClass.isAssignableFrom(TimerViewModel::class.java)){
    @Suppress("UNCHECKED_CAST")
    return TimerViewModel(repo)as T
}
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}