package com.example.pomodorowatch.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.pomodorowatch.MainActivity
import com.example.pomodorowatch.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val CHANNEL_ID = "TimerChannel"
    private val NOTIFICATION_ID = 1
    private var timerJob: Job? = null
    private val servceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START" -> {
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
        try {
            createNotificationChannel()
            // استدعاء النص من strings.xml
            startForeground(NOTIFICATION_ID, builNotification(getString(R.string.focus_time)))
        } catch (e: Exception) {
            Log.e("TimerService", "الإشعار اترفض بس العداد هيكمل شغل عادي", e)
        }
        runTimer()
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

        val cancelIntent = Intent(this, TimerService::class.java).apply {
            action = "CANCEL"
        }
        val cancelPendidg = PendingIntent.getService(
            this,
            2,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // استدعاء أسماء الزراير من strings.xml
        val pauseStr = getString(R.string.pause)
        val resumeStr = getString(R.string.resume)
        val cancelStr = getString(R.string.cancel)
val contentIntent= Intent(this, MainActivity::class.java).apply{
    flags= Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
}
        val contentPendingIntent= PendingIntent.getActivity(this,0,contentIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)


        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.pomodoro_timer_title)) // العنوان من strings
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // تأكد إن دي الأيقونة اللي إنت عاوزها
            .setOngoing(true)
            .setContentIntent(contentPendingIntent)
            .addAction(
                if (isRunning) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play,
                if (isRunning) pauseStr else resumeStr,
                pauseResumePending
            )
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, cancelStr, cancelPendidg)
            .build()
    }

    private fun updateNotification() {
        val timeRemaining = TimerManager.timerRemaining.value
        val minutes = timeRemaining / 60
        val seconds = timeRemaining % 60 // تم تصليح الغلطة هنا عشان الثواني تتحسب صح
        val timeString = String.format("%02d:%02d", minutes, seconds)

        // دمج الوقت مع النص المترجم
        val notification = builNotification(getString(R.string.time_left, timeString))
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun resumeTimer() {
        TimerManager.timerState.value = TimerState.RUNNING
        runTimer()
        updateNotification()
    }

    private fun pauseTimer() {
        TimerManager.timerState.value = TimerState.PAUSED
        timerJob?.cancel()
        updateNotification()
    }

    private fun playSound(soundResId: Int) {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(applicationContext, soundResId)
            mediaPlayer?.start()
        } catch (e: Exception) {
            Log.e("TimerService", "Error playing sound", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // اسم القناة من strings.xml
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.N)
    private fun runTimer() {
        timerJob?.cancel()
        timerJob = servceScope.launch {
            while (TimerManager.timerRemaining.value > 0 && TimerManager.timerState.value == TimerState.RUNNING) {
                delay(1000)
                if (TimerManager.timerState.value != TimerState.RUNNING) break

                TimerManager.timerRemaining.value -= 1
                try {
                    updateNotification()
                } catch (e: Exception) {}
            }

            if (TimerManager.timerRemaining.value <= 0L) {
                finishTimerSuccessfully()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun cancelTimer() {
        playSound(R.raw.faield_sound)
        TimerManager.timerState.value = TimerState.IDLE
        timerJob?.cancel()

        TimerManager.timerRemaining.value = TimerManager.totalTime.value

        try {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } catch (e: Exception) { }

        servceScope.launch {
            delay(2000)
            stopSelf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun finishTimerSuccessfully() {
        TimerManager.timerState.value = TimerState.IDLE
        playSound(R.raw.success_sound)

        try {
            stopForeground(STOP_FOREGROUND_DETACH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.notify(NOTIFICATION_ID, NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.session_completed)) // من strings
                .setContentText(getString(R.string.great_job_tree)) // من strings
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()
            )
        } catch (e: Exception) {}

        servceScope.launch {
            delay(2000)
            TimerManager.timerRemaining.value = TimerManager.totalTime.value
            stopSelf()
        }
    }
}