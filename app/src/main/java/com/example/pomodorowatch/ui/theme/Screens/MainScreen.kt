package com.example.pomodorowatch.ui.theme.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.pomodorowatch.ViewModel.TimerViewModel
import com.example.pomodorowatch.ui.theme.PrimaryGreen

@Composable
fun Mainscreen (viewModel: TimerViewModel){
    var selecctedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                icon = { Text("⏱️") },
                    label = {Text(stringResource( com.example.pomodorowatch.R.string.timer))},
                    selected = selecctedTab==0,
                    onClick = {selecctedTab=0},
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryGreen,
                        selectedTextColor = PrimaryGreen,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Text("🌳") },
                    label = { Text(stringResource(com.example.pomodorowatch.R.string.myForest)) },
                    selected = selecctedTab==1,
                    onClick = {selecctedTab=1},
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryGreen,
                        selectedTextColor = PrimaryGreen,
                        indicatorColor = Color.Transparent
                    ))
            }
        }
    ) {paddingValues ->
        Surface(modifier =  Modifier.padding(paddingValues)) {
            if (selecctedTab==0){
                TimerScreen(viewModel)
            }else{
                ForestScreen()
            }
        }
    }
}