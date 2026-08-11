package com.example.pomodorowatch.ui.Screens

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.example.pomodorowatch.ui.theme.ThemeManager

@Composable
fun ThemeToggleButton() {
    val isDarkTheme by ThemeManager.isDarkTheme.collectAsState()

    IconButton(
        onClick = { ThemeManager.toggleTheme() }
    ) {
        // بنستخدم إيموجي بسيط للتبديل (ممكن تبدله بـ Icon لو حابب)
        Text(
            text = if (isDarkTheme) "☀️" else "🌙",
            fontSize = 24.sp
        )
    }
}