package com.example.pomodorowatch

import TimerViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodorowatch.Data.LocalStorage.TreeDatabase
import com.example.pomodorowatch.Repositories.TreeSessionsRepo
import com.example.pomodorowatch.ui.theme.PomodoroWatchTheme
import com.example.pomodorowatch.ui.Screens.Mainscreen
import com.example.pomodorowatch.ui.theme.ThemeManager
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val timerViewModel: TimerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.init(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // بيشوف لو المستخدم لسه مداناش الصلاحية
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // بيطلع رسالة (Popup) للمستخدم يطلب منه الموافقة على الإشعارات
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
        val database= TreeDatabase.getDatabase(this)
        val repos= TreeSessionsRepo(database.treeSessionDao())
        val factory= TimerViewModelFactory(repos)

        enableEdgeToEdge()
        setContent{
        val isDarkTheme by ThemeManager.isDarkTheme.collectAsState()

            PomodoroWatchTheme(darkTheme =isDarkTheme ){
                Surface(modifier = Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background ) {
                    Mainscreen(viewModel = viewModel(factory=factory))
                }
            }
        }
    }
}

