package com.example.pomodorowatch.ui.theme.Screens

import android.R.attr.contentDescription
import android.widget.ImageButton
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomodorowatch.Data.LocalStorage.TreeSession
import com.example.pomodorowatch.R
//import com.example.pomodorowatch.ViewModel.TimerViewModel
import com.example.pomodorowatch.ui.theme.PrimaryGreen

@Composable
fun SessionDetailScreen(session: TreeSession, onBackClick: () -> Unit) {
    val date = java.text.SimpleDateFormat("dd MMMM yyyy, hh:mm a", java.util.Locale.getDefault())
        .format(java.util.Date(session.timeStamp))
    val treeRes =
        if (session.isSuccessful) R.drawable.green_tree_details_screen else R.drawable.withered_tree
    val statusColor = if (session.isSuccessful) PrimaryGreen else Color.Red
    val statusText = if (session.isSuccessful) "Completed Successfully" else "Given Up Early"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF6F4)) // لون الخلفية الهادي بتاع التطبيق
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // زرار الرجوع (ممكن تحط Icon بداله)
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "back icon button."
                )
            }
            Text(
                text = " Back to Forest",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen,
                modifier = Modifier
//                    .align(Alignment.Start)
//                    .clickable { onBackClick() }
            )
        }

        // دائرة جمالية ورا الشجرة الكبيرة
        Box(
            modifier = Modifier
                .size(360.dp)
                .background(Color.White.copy(alpha = 0.6f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = treeRes),
                contentDescription = "Big Tree",
                modifier = Modifier.size(500.dp).clip(RoundedCornerShape(20.dp))
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // كارت التفاصيل الأنيق
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Session Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryGreen
                )

                DetailRow(label = "Date & Time", value = date)
                DetailRow(label = "Duration", value = "${session.durationInMinutes} Minutes")
                DetailRow(label = "Stage Reached", value = "Stage ${session.treeString}")

                // سطر الحالة الملون
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Status",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = statusText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }
        }
    }
}

// دالة مساعدة عشان نرتب بيها السطور في كارت التفاصيل بشكل نظيف
@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 16.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
        Text(text = value, fontSize = 16.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
    }
}