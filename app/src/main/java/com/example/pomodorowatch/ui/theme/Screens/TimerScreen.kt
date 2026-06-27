package com.example.pomodorowatch.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.pomodorowatch.R
import com.example.pomodorowatch.ViewModel.TimerViewModel
import com.example.pomodorowatch.ui.theme.ForestGreen
import com.example.pomodorowatch.ui.theme.PrimaryGreen
import com.example.pomodorowatch.ui.theme.ProgressBackground

@Preview
@Composable
fun TimerScreen(viewmodel: TimerViewModel= TimerViewModel()){
//

    val timeLeft by viewmodel.timeLeft.collectAsState()
    val isRunning by viewmodel.isTimerRunning.collectAsState()
    val treeStage by viewmodel.treeStage.collectAsState()
    val time = viewmodel.time

    val treeStageRes= when(treeStage){
        1-> R.drawable.ic_tree_sprout_00_02
        2-> R.drawable.ic_tree_sprout_01svg_02
        3->R.drawable.ic_tree_sprout_02_02
        else -> R.drawable.ic_tree_sprout_03_02

    }



    val minutes=timeLeft / 60
    val seconds=timeLeft % 60
    val timeString = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    val progress =timeLeft.toFloat()/(time *60).toFloat()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEDF6F4))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(32.dp)
        )
        Text(text = "PomoTree 🌱",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen
        )
        Text(text = stringResource( R.string.Readytofocus), fontSize = 16.sp,
            color=Color.Gray,
            modifier = Modifier.padding(top = 8.dp))

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier.size(250.dp),
            contentAlignment = Alignment.Center
            ){

            CircularProgressIndicator(
            progress = 1f ,
            color= ProgressBackground,
                strokeWidth = 12.dp,
                modifier = Modifier.fillMaxSize()
                )
            CircularProgressIndicator(
                progress =progress ,
                color=PrimaryGreen,
                strokeWidth = 12.dp,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text=timeString, fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryGreen)
                Text(text =stringResource( R.string.Focus),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color=ForestGreen,
                letterSpacing = 2.sp)
            }
        }
            Spacer(Modifier.height(16.dp))
//            Text(text="🌱", fontSize = 48.sp)
        Image(painter = painterResource(id=treeStageRes),
            contentDescription = "Tree Stage",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 8.dp)
            )


        Text(text= if (!isRunning)
            stringResource( R.string.Yourtreeiswaitingtogrow)
        else
            stringResource( R.string.YourtreeisgrowingKeepgoing),
                fontSize = 16.sp,
                color = PrimaryGreen,
                modifier = Modifier.padding(top=8.dp, bottom = 8.dp))
            Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = {
                    if (isRunning) viewmodel.StopTimerEarly()
                    else viewmodel.startTimer()
                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = ForestGreen),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                Text(
                    text = if (isRunning) stringResource(R.string.GiveUp) else
                        stringResource(R.string.PlanetyourTreenow),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
    }

}