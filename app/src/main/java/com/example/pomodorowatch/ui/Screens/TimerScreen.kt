package com.example.pomodorowatch.ui.Screens

import IOSTimePicker
import TimerViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pomodorowatch.R
import com.example.pomodorowatch.ui.theme.ForestGreen
import com.example.pomodorowatch.ui.theme.PrimaryGreen
import com.example.pomodorowatch.ui.theme.ProgressBackground

//@Preview
@Composable
fun TimerScreen(viewmodel: TimerViewModel) {

    val context = LocalContext.current
    val timeLeft by viewmodel.timeLeft.collectAsState()
    val isRunning by viewmodel.isTimerRunning.collectAsState()
    val treeStage by viewmodel.treeStage.collectAsState()
    val totalTime by viewmodel.totalTime.collectAsState()
    var showTimeDialog by remember { mutableStateOf(false) }
    val progress = if (totalTime > 0) timeLeft.toFloat() / totalTime.toFloat() else 1f

    val treeStageRes = when (treeStage) {
        1 -> R.drawable.ic_tree_sprout_00_02
        2 -> R.drawable.ic_tree_sprout_01svg_02
        3 -> R.drawable.ic_tree_sprout_02_02
        else -> R.drawable.ic_tree_sprout_03_02

    }


    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeString = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
//    val progress =timeLeft.toFloat()/(time *60).toFloat()
//
//if(!isRunning) {
//    Column(
//        modifier =
//            Modifier.fillMaxWidth()
//                .padding(vertical = 16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "اختر مدة التركيز",
//            fontSize = 14.sp,
//            color = Color.Gray,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//        IOSTimePicker(
//            currentMinutes = totalTime / 60, // قراءة الوقت الحالي
//            onTimeSelected = { selectedMinutes ->
//                viewmodel.setCustomTime(selectedMinutes) // تحديث الوقت في الـ ViewModel
//            }
//        )
//    }
//}
    if (showTimeDialog) {
        Dialog(onDismissRequest = { showTimeDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.choose_focus_duration),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // البكرة بتاعتنا جوه الـ Dialog
                    IOSTimePicker(
                        currentMinutes = (totalTime / 60).toInt(),
                        onTimeSelected = { selectedMinutes ->
                            viewmodel.setCustomTime(selectedMinutes)
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // زرار التأكيد
                    Button(
                        onClick = { showTimeDialog = false }, // بيقفل النافذة
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.done),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp).padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ThemeToggleButton()
        }
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        Text(
            text = "PomoTree 🌱",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen
        )
        Text(
            text = stringResource(R.string.Readytofocus), fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier.size(250.dp),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                progress = 1f,
                color = ProgressBackground,
                strokeWidth = 12.dp,
                modifier = Modifier.fillMaxSize()
            )
            CircularProgressIndicator(
                progress = progress,
                color = PrimaryGreen,
                strokeWidth = 12.dp,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeString, fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryGreen,
                    modifier = Modifier.clickable(enabled = !isRunning)
                    {
                        showTimeDialog = true
                    })
                Text(
                    text = if (!isRunning) stringResource(R.string.tap_to_edit) else stringResource(
                        R.string.Focus
                    ),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestGreen,
                    letterSpacing = 2.sp
                )
            }
        }
        Spacer(Modifier.height(16.dp))
//            Text(text="🌱", fontSize = 48.sp)
        Image(
            painter = painterResource(id = treeStageRes),
            contentDescription = "Tree Stage",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 8.dp)
        )


        Text(
            text = if (!isRunning)
                stringResource(R.string.Yourtreeiswaitingtogrow)
            else
                stringResource(R.string.YourtreeisgrowingKeepgoing),
            fontSize = 16.sp,
            color = PrimaryGreen,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

//        Box(modifier = Modifier.padding(24.dp)) {
//            Button(
//                onClick = {
//                    if (isRunning) viewmodel.pauseTimer()
//                    else viewmodel.startTimer()
//                },
//                colors = ButtonDefaults
//                    .buttonColors(containerColor = ForestGreen),
//                shape = RoundedCornerShape(20.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(64.dp)
//            ) {
//                Text(
//                    text = if (isRunning) stringResource(R.string.GiveUp) else
//                        stringResource(R.string.PlanetyourTreenow),
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//            }
//        }
//        Box(modifier = Modifier.padding(horizontal =
//            24.dp).width(110.dp)) {
//            Button(
//                onClick = {
//                        viewmodel.StopTimerEarly()
//                },
//                colors = ButtonDefaults
//                    .buttonColors(containerColor = Color.Red),
//                shape = RoundedCornerShape(20.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(64.dp)
//            ) {
//                Text(
//                    text  ="cancel",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//            }
//        }
// قسم الأزرار
        Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            if (!isRunning && timeLeft == totalTime) {
                // حالة البداية: زرار واحد للتشغيل
                Button(
                    onClick = { viewmodel.startTimer(context) },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                ) {
                    Text(
                        text = stringResource(R.string.PlanetyourTreenow),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                // حالة التشغيل أو الإيقاف المؤقت: زرارين جنب بعض (Pause/Resume و Cancel)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // زرار الإيقاف المؤقت / الاستئناف
                    Button(
                        onClick = {
                            if (isRunning) viewmodel.pauseTimer(context)
                            else viewmodel.resumeTimer(context)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                    ) {
                        Text(
                            text = if (isRunning) stringResource(R.string.pause) else stringResource(
                                R.string.resume
                            ),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // زرار الإلغاء (Give Up) اللي بيحفظ الشجرة كفاشلة
                    Button(
                        onClick = { viewmodel.StopTimerEarly(context) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
    }

}