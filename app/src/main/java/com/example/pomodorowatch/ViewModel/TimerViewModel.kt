package com.example.pomodorowatch.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {
     val time =25
  private val Pomodoro_Time_Seconds=time*60
  private val _timeLeft= MutableStateFlow(Pomodoro_Time_Seconds)
    val timeLeft=_timeLeft.asStateFlow()
  private val _isTimerRunning= MutableStateFlow(false)
    val isTimerRunning=_isTimerRunning.asStateFlow()
  private val _treeStage= MutableStateFlow(1)
    val treeStage =_treeStage.asStateFlow()

    private var timerJob : Job? =null

    fun startTimer(){
        if(_isTimerRunning.value)
            return
        _isTimerRunning.value=true
    timerJob=viewModelScope.launch {
        while (_isTimerRunning.value&&_timeLeft.value>0) {
            delay(1000L)
            _timeLeft.value -= 1
            updateTreeStage()
        }
        if(_timeLeft.value==0)
            finishTimerSuccessfuly()
    }

    }

    fun StopTimerEarly() {
        timerJob?.cancel()
        _isTimerRunning.value=false
        _timeLeft.value=Pomodoro_Time_Seconds
        _treeStage.value=1
    }
     fun finishTimerSuccessfuly() {
        _isTimerRunning.value=false
        _timeLeft.value=Pomodoro_Time_Seconds
        _treeStage.value=4
    }

     fun updateTreeStage() {
        val timePassed = Pomodoro_Time_Seconds - _timeLeft.value
        _treeStage.value = when {
            timePassed < Pomodoro_Time_Seconds / 4->1
            timePassed < Pomodoro_Time_Seconds / 2->2
            timePassed < (Pomodoro_Time_Seconds * 3)/4->3
            else->4

        } as Int


    }


}