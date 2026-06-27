package com.example.pomodorowatch.ui.theme.Screens

import android.R
import android.graphics.drawable.Icon

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomodorowatch.ui.theme.ForestGreen
import com.example.pomodorowatch.ui.theme.PrimaryGreen
import com.example.pomodorowatch.ui.theme.TreeBackground
import com.example.pomodorowatch.ui.theme.WitheredBrown
@Preview
@Composable
fun ForestScreen(){
    Column(modifier = Modifier.fillMaxSize()
        .background(Color(0xFFEDF6F4))
        .verticalScroll(rememberScrollState())
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(text =   stringResource( com.example.pomodorowatch.R.string.MyForest) ,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen
            )

        Text(text = stringResource(
            com.example.pomodorowatch.R.string.sessionscompletedkeepgoing),
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp))
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            StateCard(modifier = Modifier.weight(1f),
                icon = "🌳",
                title = stringResource( com.example.pomodorowatch.R.string.TreesGrown),
                count = "15", color=ForestGreen)
            StateCard(modifier = Modifier.weight(1f),
                icon = "🍂",
                title = stringResource( com.example.pomodorowatch.R.string.WitheredTrees),
                count = "2", color = WitheredBrown)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(stringResource( com.example.pomodorowatch.R.string.yourTrees)
            , fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
            letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Card(colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize()
                    .height(360.dp),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(15){

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .background(TreeBackground,
                            RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Text("🌳", fontSize = 24.sp)
                    }

                }

            }

        }
    }

}

@Composable
fun StateCard(modifier: Modifier,icon: String,title : String,count : String,color: Color) {
    Card ( modifier .height(150.dp),
        colors= CardDefaults.cardColors(containerColor = Color.White),
        elevation= CardDefaults.cardElevation(defaultElevation =2.dp),
        shape = RoundedCornerShape(16.dp)
        ){
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
