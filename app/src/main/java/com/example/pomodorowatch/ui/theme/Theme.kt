package com.example.pomodorowatch.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// =========================================
// 1. لوحة الألوان للوضع الداكن (Dark Mode)
// =========================================
private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF121212),       // لون الشاشة بالكامل (أسود رمادي)
    surfaceBright = Color(0xFF212225),     //اللون الخاص بالbottom bar بتاع الscafold
    surface = Color(0xFF1E1E1E),          // لون الكروت والـ Dialogs
    onBackground = Color(0xFFE0E0E0),     // لون النصوص الأساسية
    onSurface = Color(0xFFE0E0E0),        // لون النصوص جوه الكروت
    primary = PrimaryGreen,               // اللون الأخضر الأساسي بتاعك (بيفضل زي ما هو)
    secondary = ForestGreen
)

// =========================================
// 2. لوحة الألوان للوضع الفاتح (Light Mode)
// =========================================
private val LightColorScheme = lightColorScheme(
    background = Color(0xFFEDF6F4),       // لون الخلفية الهادي بتاعك
    surfaceBright = Color.White,
    surface = Color.White,                // لون الكروت والـ Dialogs
    onBackground = PrimaryGreen,          // لون النصوص الأساسية (أو أسود لو حابب)
    onSurface = Color.DarkGray,           // لون النصوص جوه الكروت
    primary = PrimaryGreen,
    secondary = ForestGreen
)

@Composable
fun PomodoroWatchTheme(
    // لو عاوز الثيم يتغير أوتوماتيك مع نظام الموبايل، استخدم isSystemInDarkTheme()
    // ولو عاوز تربطه بالزرار بتاعنا، هتمرر القيمة من الـ ThemeManager
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // تحديد لوحة الألوان بناءً على الوضع
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // لو عندك ملف للخطوط
        content = content
    )
}