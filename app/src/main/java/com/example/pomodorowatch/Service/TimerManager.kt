package com.example.pomodorowatch.Service

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow

enum class TimerState {IDLE,RUNNING,PAUSED }
object TimerManager{
    // حالة العداد الحالية
    val timerState= MutableStateFlow(TimerState.IDLE)
    // الوقت المتبقي بالثواني (هنفترض إنه بيبدأ بـ 0)
    val timerRemaining= MutableStateFlow(25* 60L)
    // إجمالي وقت الجلسة عشان نقدر نحسب شريط التقدم (Progress bar)
    val totalTime= MutableStateFlow(25* 60L)

}