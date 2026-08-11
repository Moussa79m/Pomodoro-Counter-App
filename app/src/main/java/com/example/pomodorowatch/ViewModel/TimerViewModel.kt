import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodorowatch.Data.LocalStorage.TreeSession
import com.example.pomodorowatch.Repositories.TreeSessionsRepo
import com.example.pomodorowatch.Service.TimerManager
import com.example.pomodorowatch.Service.TimerService
import com.example.pomodorowatch.Service.TimerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

//class TimerViewModel(private val repo: TreeSessionsRepo): ViewModel() {
//    private val defaultTimeInSeconds = 25 * 60
//  private val _timeLeft= MutableStateFlow(defaultTimeInSeconds)
//    val timeLeft=_timeLeft.asStateFlow()
//  private val _isTimerRunning= MutableStateFlow(false)
//    val isTimerRunning=_isTimerRunning.asStateFlow()
//  private val _treeStage= MutableStateFlow(1)
//    val treeStage =_treeStage.asStateFlow()
//    private val _totalTime= MutableStateFlow(defaultTimeInSeconds)
//    val totalTime=_totalTime.asStateFlow()
//    // ==========================================
//    // 2. تمرير الإحصائيات من الـ Repository عشان الشاشات التانية تقرأها
//    // ==========================================
//    val allSessions =repo.allSessions.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        initialValue = emptyList()
//    )
//    val successfulTreesCount=repo.successfulTreeCount.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        initialValue = 0
//    )
//    val witheredTreeCount=repo.witheredTreesCount.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        initialValue = 0
//    )
//    val totalFocusMinutes=repo.totalFocusMinutes.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        initialValue = 0
//    )
//    val totalMinutes=repo.totalMinutes.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        initialValue = 0
//    )
//
//    private var timerJob : Job? =null
//
//    fun setCustomTime(minutes : Int) {
//        if(!_isTimerRunning.value){
//            val timeInSeconds=minutes*60
//            _totalTime.value=timeInSeconds
//            _timeLeft.value=timeInSeconds
//            _treeStage.value=1
//        }
//    }
//
//    fun startTimer(){
//        if(_isTimerRunning.value)
//            return
//        if(_timeLeft.value==0){
//           _timeLeft.value=_totalTime.value
//           _treeStage.value=1
//        }
//        _isTimerRunning.value=true
//    timerJob=viewModelScope.launch {
//        while (_isTimerRunning.value&&_timeLeft.value>0) {
//            delay(1000L.milliseconds)
//            _timeLeft.value -= 1
//            updateTreeStage()
//        }
//        if(_timeLeft.value==0&& _isTimerRunning.value)
//            finishTimerSuccessfuly()
//    }
//
//    }
//
//    fun StopTimerEarly() {
//        timerJob?.cancel()
//        _isTimerRunning.value=false
//        val timePassed=_totalTime.value-_timeLeft.value
//        if (timePassed>60){
//            saveSessions(false, finalStage = _treeStage.value)
//        }
//        _timeLeft.value=_totalTime.value
//        _treeStage.value=1
//    }
//     fun finishTimerSuccessfuly() {
//        _isTimerRunning.value=false
//        _timeLeft.value=_totalTime.value
//        _treeStage.value=4
//         saveSessions(true, finalStage = 4)
//
//    }
//
//     fun updateTreeStage() {
//        val timePassed = _totalTime.value - _timeLeft.value
//        _treeStage.value = when {
//            timePassed < _totalTime.value / 4->1
//            timePassed < _totalTime.value / 2->2
//            timePassed < (_totalTime.value * 3)/4->3
//            else->4
//
//        } as Int }
//    fun saveSessions(isSuccessful: Boolean,finalStage: Int){
//        viewModelScope.launch {
//            val session = TreeSession(
//                durationInMinutes = _totalTime.value /60,
//                isSuccessful = isSuccessful,
//                treeString = finalStage,
//                timeStamp = System.currentTimeMillis()
//                )
//            repo.insertSession(session)
//        }
//    }
//    fun pauseTimer(){
//        timerJob?.cancel()
//        _isTimerRunning.value=false
//    }
//
//}


class TimerViewModel(private val repo: TreeSessionsRepo) : ViewModel() {

    // ==========================================
    // 1. الإحصائيات (عشان شاشة الغابة)
    // ==========================================
    val allSessions = repo.allSessions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val successfulTreesCount = repo.successfulTreeCount.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000L), initialValue = 0
    )
    val witheredTreeCount = repo.witheredTreesCount.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000L), initialValue = 0
    )
    val totalFocusMinutes = repo.totalFocusMinutes.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000L), initialValue = 0
    )
    val totalMinutes = repo.totalMinutes.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000L), initialValue = 0
    )

    // ==========================================
    // 2. متغيرات الشاشة (مربوطة بالـ Service مباشرة)
    // ==========================================

    val timeLeft = TimerManager.timerRemaining
    val totalTime = TimerManager.totalTime

    // تحويل حالة الـ Service لمتغير boolean عشان الشاشة بتاعتك تفهمه
    val isTimerRunning = TimerManager.timerState.map { it == TimerState.RUNNING }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    // حساب مرحلة الشجرة أوتوماتيكياً بناءً على الوقت المتبقي والإجمالي
    val treeStage =
        combine(TimerManager.timerRemaining, TimerManager.totalTime) { remaining, total ->
            if (total == 0L) return@combine 1
            val passed = total - remaining
            when {
                passed < total / 4 -> 1
                passed < total / 2 -> 2
                passed < (total * 3) / 4 -> 3
                else -> 4
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)


    // ==========================================
    // 3. مراقبة انتهاء الوقت للحفظ التلقائي
    // ==========================================
    init {
        viewModelScope.launch {
            TimerManager.timerRemaining.collect { remaining ->
                // لو الوقت وصل لصفر، والعداد كان شغال (عشان نتجنب الحفظ وقت الـ Cancel)
                if (remaining == 0L && TimerManager.totalTime.value > 0L) {
                    saveSessions(isSuccessful = true, finalStage = 4)
                }
            }
        }
    }

    // ==========================================
    // 4. دوال التحكم والـ Service
    // ==========================================

    fun setCustomTime(minutes: Int) {
        if (TimerManager.timerState.value != TimerState.RUNNING) {
            val seconds = minutes * 60L
            TimerManager.totalTime.value = seconds
            TimerManager.timerRemaining.value = seconds
        }
    }

    fun startTimer(context: Context) {
        if (TimerManager.timerState.value == TimerState.RUNNING) return

        // لو الوقت كان خلص قبل كده، نرجعه للقيمة الأساسية
        if (TimerManager.timerRemaining.value == 0L) {
            TimerManager.timerRemaining.value = TimerManager.totalTime.value
        }

        val intent = Intent(context, TimerService::class.java).apply {
            action = "START"
            putExtra("MINUTES", (TimerManager.totalTime.value / 60).toInt())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    fun StopTimerEarly(context: Context) {
        val timePassed = TimerManager.totalTime.value - TimerManager.timerRemaining.value

        // لو عدى أكتر من 60 ثانية، نحفظها كشجرة ميتة
        if (timePassed > 60) {
            saveSessions(isSuccessful = false, finalStage = treeStage.value)
        }

        // نقفل السيرفيس
        val intent = Intent(context, TimerService::class.java).apply {
            action = "CANCEL" }
        context.startService(intent)

        // نرجع العداد لحالته الأصلية
//        TimerManager.timerRemaining.value = TimerManager.totalTime.value
    }

    fun pauseTimer(context: Context) {
        val intent = Intent(context, TimerService::class.java).apply { action = "PAUSE" }
        context.startService(intent)
    }

    fun resumeTimer(context: Context) {
        val intent = Intent(context, TimerService::class.java).apply { action = "RESUME" }
        context.startService(intent)
    }

    private fun saveSessions(isSuccessful: Boolean, finalStage: Int) {
        viewModelScope.launch {
            val session = TreeSession(
                durationInMinutes = (TimerManager.totalTime.value / 60).toInt(),
                isSuccessful = isSuccessful,
                treeString = finalStage, // تأكد إن الاسم ده مطابق للـ Entity عندك
                timeStamp = System.currentTimeMillis()
            )
            repo.insertSession(session)
        }
    }
}