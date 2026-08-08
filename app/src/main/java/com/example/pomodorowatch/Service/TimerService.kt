package com.example.pomodorowatch.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.pomodorowatch.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer

class TimerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val CHANNEL_ID = "TimerChannel"
    private val NOTIFICATION_ID = 1
    private var timerJob: Job? = null
    private val servceScope = CoroutineScope(Dispatchers.Main)

    // before
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        when (intent?.action) {
//            "START" -> startForgroundService()
//            "PAUSE" -> {}
//            "CANCEL" -> {
//                playSound(R.raw.success_sound)
//                stopSelf()
//            }
//
//            "SUCCESS" -> {
//                playSound(R.raw.faield_sound)
//                stopSelf()
//            }
//        }
//        return START_STICKY
//    }

    //after
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START" -> {
                // بنستقبل الوقت اللي اليوزر اختاره بالدقائق ونحوله لثواني
                val minutes = intent.getIntExtra("MINUTES", 25)
                startTimer(minutes * 60L)
            }

            "PAUSE" -> pauseTimer()
            "RESUME" -> resumeTimer()
            "CANCEL" -> cancelTimer()
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun startTimer(seconds: Long) {
        TimerManager.totalTime.value = seconds
        TimerManager.timerRemaining.value = seconds
        TimerManager.timerState.value = TimerState.RUNNING
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, builNotification("Focus Time"))
        runTimer()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun runTimer() {
        timerJob?.cancel()
        timerJob = servceScope.launch {
            while (TimerManager.timerRemaining.value > 0 && TimerManager.timerState.value == TimerState.RUNNING)
                delay(1000)
            TimerManager.timerRemaining.value -= 1
            updateNotification()
        }
        if (TimerManager.timerRemaining.value <= 0L) {
            finishTimerSuccessfully()
        }
    }


    private fun builNotification(contentText: String): android.app.Notification {
        val isRunning = TimerManager.timerState.value == TimerState.RUNNING
        val pauseResumeIntent = Intent(this, TimerService::class.java).apply {
            action = if (isRunning) "PAUSE" else "RESUME"

        }
        val pauseResumePending = PendingIntent.getService(
            this,
            1,
            pauseResumeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        val cancelIntent = Intent(
            this,
            TimerService::class.java
        ).apply {
            action = "CANCEL"
        }
        val cancelPendidg = PendingIntent.getService(
            this,
            2,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Pomodoro Timer")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .addAction(
                if (isRunning) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play,
                if (isRunning) "Pause" else "Resume",
                pauseResumePending
            )
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Cancel", cancelPendidg)
            .build()
    }

    private fun updateNotification() {
        val timeRemaining = TimerManager.timerRemaining.value
        val minutes = timeRemaining / 60
        val seconds = minutes / 60
        val timeString = String.format("%02d:%02d", minutes, seconds)
        val notification = builNotification("Time left: $timeString")
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun finishTimerSuccessfully() {
        playSound(R.raw.success_sound)
        TimerManager.timerState.value = TimerState.IDLE
        stopForeground(STOP_FOREGROUND_DETACH)
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(
            NOTIFICATION_ID, NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Session Completed! 🌳")
                .setContentText("Great job! You grew a new tree.")
                .setSmallIcon(R.drawable.ic_tree_sprout_03_02)
                .build()
        )
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun cancelTimer() {
        playSound(R.raw.faield_sound)
        TimerManager.timerState.value = TimerState.IDLE
        timerJob?.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun resumeTimer() {
        TimerManager.timerState.value= TimerState.RUNNING
        runTimer()
        updateNotification()

    }

    private fun pauseTimer() {
        TimerManager.timerState.value= TimerState.PAUSED
        timerJob?.cancel()
        updateNotification()
    }


    private fun playSound(soundResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer?.start()

    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun startForgroundService() {
//        createNotificationChannel()
//
//        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
//            .setContentTitle("Pomodoro Timer")
//            .setContentText("Focus session is running...")
//            .setSmallIcon(R.drawable.ic_tree_sprout_02_02)
//            .setOngoing(true)
//            .build()
//
//        startForeground(1,notification)
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel() {
//        val channel = NotificationChannel(
//            CHANNEL_ID,
//            "Timer Service",
//            NotificationManager.IMPORTANCE_LOW
//        )
//        val manager = getSystemService(NotificationManager::class.java)
//        manager.createNotificationChannel(channel)
//
//    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel= NotificationChannel(CHANNEL_ID,"Timer Service", NotificationManager.IMPORTANCE_LOW)
            val manager=getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }


    override fun onBind(p0: Intent?): IBinder? = null
}