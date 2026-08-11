package com.example.pomodorowatch.ui.Screens


import TimerViewModel
import androidx.activity.compose.BackHandler
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
import com.example.pomodorowatch.Data.LocalStorage.TreeSession


import com.example.pomodorowatch.ui.theme.PrimaryGreen

//with animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import com.example.pomodorowatch.R

// ... بقية الـ imports بتاعتك

@Composable
fun Mainscreen(viewModel: TimerViewModel) {
    var selecctedTab by remember { mutableStateOf(0) }
    var sessionToViewDetails by remember { mutableStateOf<TreeSession?>(null) }

    // حاوية الأنيميشن السحرية
    AnimatedContent(
        targetState = sessionToViewDetails,
//        modifier = Modifier.fillMaxSize(), // 👈 السر الأول: تثبيت الحجم لمنع التقطيع
        transitionSpec = {
            if (targetState != null) {
                // 👈 السر التاني: دمج حركة السحب مع ظهور واختفاء الشفافية (Fade)
                (slideInHorizontally(
                    animationSpec = tween(200),
                    initialOffsetX = { fullWidth -> fullWidth }
                ) + fadeIn(animationSpec = tween(200))) togetherWith (
                        slideOutHorizontally(
                            animationSpec = tween(200),
                            targetOffsetX = { fullWidth -> -fullWidth / 2 }
                        ) + fadeOut(animationSpec = tween(200)))
            } else {
                (slideInHorizontally(
                    animationSpec = tween(200),
                    initialOffsetX = { fullWidth -> -fullWidth  } // تم تعديلها لـ /2 عشان تبقى متناسقة مع الدخول
                )  togetherWith (
                        slideOutHorizontally(
                            animationSpec = tween(200),
                            targetOffsetX = { fullWidth -> fullWidth }
                        ) ))
            }
        },
        label = "Screen Transition"
    ) { targetSession ->

        // هنا بنشوف الحالة اللي الـ AnimatedContent بتبنيها دلوقتي
        if (targetSession != null) {

            // شاشة التفاصيل
            BackHandler {
                sessionToViewDetails = null
            }

            SessionDetailScreen(
                session = targetSession,
                onBackClick = { sessionToViewDetails = null }
            )

        } else {

            // شاشة الغابة والتايمر
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceBright
                    ) {
                        NavigationBarItem(
                            icon = { Text("⏱️") },
                            label = { Text(stringResource(R.string.timer)) },
                            selected = selecctedTab == 0,
                            onClick = { selecctedTab = 0 },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryGreen,
                                selectedTextColor = PrimaryGreen,
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            icon = { Text("🌳") },
                            label = { Text(stringResource(R.string.myForest)) },
                            selected = selecctedTab == 1,
                            onClick = { selecctedTab = 1 },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryGreen,
                                selectedTextColor = PrimaryGreen,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            ) { paddingValues ->
                Surface(modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {

                    // حاوية الأنيميشن الخاصة بالتابات السفلية
                    AnimatedContent(
                        targetState = selecctedTab,
                        modifier = Modifier.fillMaxSize(),
                        transitionSpec = {
                            // لو رايحين من التايمر (0) للغابة (1) -> اسحب للشمال
                            if (targetState > initialState) {
                                (slideInHorizontally(tween(300)) { width -> width } + fadeIn(
                                    tween(
                                        300
                                    )
                                )) togetherWith
                                        (slideOutHorizontally(tween(300)) { width -> -width } + fadeOut(
                                            tween(300)
                                        ))
                            }
                            // لو راجعين من الغابة (1) للتايمر (0) -> اسحب لليمين
                            else {
                                (slideInHorizontally(tween(300)) { width -> -width } + fadeIn(
                                    tween(
                                        300
                                    )
                                )) togetherWith
                                        (slideOutHorizontally(tween(300)) { width -> width } + fadeOut(
                                            tween(300)
                                        ))
                            }
                        },
                        label = "BottomBar Transition"
                    ) { targetTab ->
                        // هنا بنعرض الشاشة بناءً على التاب الحالي بعد ما الأنيميشن يشتغل
                        if (targetTab == 0) {
                            TimerScreen(viewModel)
                        } else {
                            ForestScreen(
                                viewmodel = viewModel,
                                onTreeClick = { clickedSession ->
                                    sessionToViewDetails = clickedSession
                                }
                            )
                        }
                    }

                }
            }

        }
    }
}
// without animation

//
//@Composable
//fun Mainscreen(viewModel: TimerViewModel) {
//    var selecctedTab by remember { mutableStateOf(0) }
//
//    // المتغير السحري: بياخد بيانات الجلسة اللي اليوزر هيضغط عليها، وقيمته الافتراضية null (مفيش حاجة مضغوطة)
//    var sessionToViewDetails by remember { mutableStateOf<TreeSession?>(null) }
//
//    // لو المتغير مش null (يعني اليوزر ضغط على شجرة) -> اعرض شاشة التفاصيل
//    if (sessionToViewDetails != null) {
//        BackHandler {
//            sessionToViewDetails = null
//        }
//
//        SessionDetailScreen(
//            session = sessionToViewDetails!!,
//            onBackClick = { sessionToViewDetails = null } // زرار الرجوع بيخلي المتغير null تاني، فترجع الغابة
//        )
//
//    }
//    // لو المتغير null -> اعرض الشاشة العادية بتاعتك بالـ Scaffold والـ BottomBar
//    else {
//        Scaffold(
//            bottomBar = {
//                NavigationBar(
//                    containerColor = Color.White
//                ) {
//                    NavigationBarItem(
//                        icon = { Text("⏱️") },
//                        label = { Text(stringResource(com.example.pomodorowatch.R.string.timer)) },
//                        selected = selecctedTab == 0,
//                        onClick = { selecctedTab = 0 },
//                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = PrimaryGreen,
//                            selectedTextColor = PrimaryGreen,
//                            indicatorColor = Color.Transparent
//                        )
//                    )
//                    NavigationBarItem(
//                        icon = { Text("🌳") },
//                        label = { Text(stringResource(com.example.pomodorowatch.R.string.myForest)) },
//                        selected = selecctedTab == 1,
//                        onClick = { selecctedTab = 1 },
//                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = PrimaryGreen,
//                            selectedTextColor = PrimaryGreen,
//                            indicatorColor = Color.Transparent
//                        )
//                    )
//                }
//            }
//        ) { paddingValues ->
//            Surface(modifier = Modifier.padding(paddingValues)) {
//                if (selecctedTab == 0) {
//                    TimerScreen(viewModel)
//                } else {
//                    ForestScreen(
//                        viewmodel = viewModel,
//                        // هنا بنقول للغابة: لما اليوزر يضغط على شجرة، ابعتي بياناتها للمتغير بتاعنا
//                        onTreeClick = { clickedSession ->
//                            sessionToViewDetails = clickedSession
//                        }
//                    )
//                }
//            }
//        }
//    }
//}

