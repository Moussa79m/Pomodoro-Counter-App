package com.example.pomodorowatch.ui.theme

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow

object ThemeManager {
    // بيبدأ بوضع فاتح (false)، ولما المستخدم يضغط على الزرار هنعكسه
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_IS_DARK = "is_dark_theme"
    private lateinit var prefs: SharedPreferences
    val isDarkTheme = MutableStateFlow(false)

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        isDarkTheme.value = prefs.getBoolean(KEY_IS_DARK, false)
    }

    fun toggleTheme() {
        val newValue = !isDarkTheme.value
        isDarkTheme.value = newValue
        prefs.edit().putBoolean(KEY_IS_DARK,newValue).apply()
    }
}