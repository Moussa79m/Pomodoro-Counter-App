package com.example.pomodorowatch.ui.theme.Screens

import TimerViewModel
import android.graphics.drawable.Icon
import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodorowatch.Data.LocalStorage.TreeSession
import com.example.pomodorowatch.R
import com.example.pomodorowatch.ui.theme.ForestGreen
import com.example.pomodorowatch.ui.theme.LightEmerald
import com.example.pomodorowatch.ui.theme.LightGreenCard
import com.example.pomodorowatch.ui.theme.PrimaryGreen
import com.example.pomodorowatch.ui.theme.TreeBackground
import com.example.pomodorowatch.ui.theme.WitheredBrown
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//@Preview
@Composable
fun ForestScreen(viewmodel: TimerViewModel, onTreeClick: (TreeSession) -> Unit) {
    val allSessions by viewmodel.allSessions.collectAsState()
    val successfulCount by viewmodel.successfulTreesCount.collectAsState()
    val witheredCount by viewmodel.witheredTreeCount.collectAsState(initial = 0)
    val totalFocusMinutes by viewmodel.totalFocusMinutes.collectAsState(initial = 0)
    val totalMinutes by viewmodel.totalMinutes.collectAsState(initial = 0)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF6F4))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(com.example.pomodorowatch.R.string.MyForest),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen
        )

        Text(
            text = "$successfulCount of ${successfulCount+witheredCount} " + stringResource(
                com.example.pomodorowatch.R.string.sessionscompletedkeepgoing
            ),
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StateCard(
                modifier = Modifier.weight(1f),
                icon = "🌳",
                title = stringResource(com.example.pomodorowatch.R.string.TreesGrown),
                count = "$successfulCount", color = ForestGreen
            )
            StateCard(
                modifier = Modifier.weight(1f),
                icon = "🍂",
                title = stringResource(com.example.pomodorowatch.R.string.WitheredTrees),
                count = "$witheredCount", color = WitheredBrown
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(90.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)) {
            val safeTotalFocusMinutes = totalFocusMinutes ?: 0
            val safeTotalMinutes = totalMinutes ?: 0

            val progressRatio = if (safeTotalFocusMinutes > 0) {
                safeTotalFocusMinutes.toFloat() / safeTotalMinutes.toFloat()
            } else {
                0f
            }

            Column(modifier = Modifier.fillMaxSize().padding(12.dp)
                , horizontalAlignment = Alignment.Start) {
                Text(
                    text = "$totalFocusMinutes / $totalMinutes Successful Minutes ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightGreenCard
                )
                Spacer(modifier = Modifier.height(12.dp))

// 3. عرض شريط التقدم
                LinearProgressIndicator(

                    progress = { progressRatio },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(8.dp)
                    ,
                    color = LightEmerald,

                    )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            stringResource(com.example.pomodorowatch.R.string.yourTrees),
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .height(360.dp),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(allSessions) { session ->
                    TreeSessionCard(session, onClick = { onTreeClick(session) })
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Box(modifier = Modifier
//                        .padding(8.dp)
//                        .aspectRatio(1f)
//                        .background(TreeBackground,
//                            RoundedCornerShape(12.dp)),
//                        contentAlignment = Alignment.Center
//                    ){
//                        Text("🌳", fontSize = 24.sp)
//                        Text("${session.durationInMinutes}", fontSize = 24.sp)
////                        Text("🌳", fontSize = 24.sp)
//
//                    }

                }

            }

        }
    }

}

@Composable
fun StateCard(modifier: Modifier, icon: String, title: String, count: String, color: Color) {
    Card(
        modifier.height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = count,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
        }
    }

}

@Composable
fun SessionCard(session: TreeSession) {

    val date =
        SimpleDateFormat("dd MMM yyyy,hh:mm a", Locale.getDefault()).format(Date(session.timeStamp))
    Column(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .background(
                TreeBackground,
                RoundedCornerShape(12.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🌳", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("${session.durationInMinutes}", fontSize = 16.sp)
            Spacer(Modifier.width(100.dp))
            Text(
                text = if (session.isSuccessful) " 🟢" else " 🔴",
                color = if (session.isSuccessful) PrimaryGreen else Color.Red
            )
        }
    }
}

@Preview
@Composable
fun PrevewTreeSessionCard() {
    val session: TreeSession = TreeSession(2, 4, true, 6, 21.0.toLong())
    TreeSessionCard(session) { }
}

@Composable
fun TreeSessionCard(
    session: TreeSession,
    onClick: () -> Unit
) {
    val treeRes = if (session.isSuccessful)
        R.drawable.green_tree
    else
        R.drawable.withered_tree
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .aspectRatio(1f)
            .padding(8.dp)
            .clickable { onClick() }, shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(contentColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )
    {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(treeRes), contentDescription = "tree status",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(550.dp)
                    .align(Alignment.Center),
            )
            Text(
                text = "${session.durationInMinutes}",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = PrimaryGreen,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color =
                            if (session.isSuccessful) LightEmerald else Color.Red,
                        shape = CircleShape
                    )
                    .align(Alignment.BottomEnd)
            )
        }
    }

}


//@Composable
//fun SessionCard(session: TreeSession) {
//    val date = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault()).format(java.util.Date(session.timeStamp))
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(16.dp), // زوايا دائرية ناعمة
//        colors = CardDefaults.cardColors(containerColor = Color.White), // كارت أبيض ناصع
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // ظل خفيف جداً
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Date: $date",
//                fontSize = 14.sp,
//                color = Color.DarkGray,
//                fontWeight = FontWeight.Medium
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = "Duration: ${session.durationInMinutes} Minutes",
//                fontSize = 14.sp,
//                color = Color.DarkGray
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = "Stage Reached: ${session.treeString}",
//                fontSize = 14.sp,
//                color = Color.DarkGray
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // سطر الحالة بلون مميز
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "Status: ",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = if (session.isSuccessful) "Success 🟢" else "Failed 🔴",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = if (session.isSuccessful) PrimaryGreen else Color.Red
//                )
//            }
//        }
//    }
//}
