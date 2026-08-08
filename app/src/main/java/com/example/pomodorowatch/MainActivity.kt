package com.example.pomodorowatch

import TimerViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodorowatch.Data.LocalStorage.TreeDatabase
import com.example.pomodorowatch.Repositories.TreeSessionsRepo
import com.example.pomodorowatch.ui.theme.PomodoroWatchTheme
import com.example.pomodorowatch.ui.theme.Screens.Mainscreen
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val timerViewMiodel: TimerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database= TreeDatabase.getDatabase(this)
        val repos= TreeSessionsRepo(database.treeSessionDao())
        val factory= TimerViewModelFactory(repos)

        enableEdgeToEdge()
        setContent {

            PomodoroWatchTheme{
                Surface(modifier = Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background ) {
                    Mainscreen(viewModel = viewModel(factory=factory))
                }
            }
        }
    }
}

