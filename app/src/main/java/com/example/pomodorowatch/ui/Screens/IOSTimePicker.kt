import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomodorowatch.ui.theme.PrimaryGreen // تأكد من استدعاء لونك

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IOSTimePicker(
    currentMinutes: Int,
    onTimeSelected: (Int) -> Unit
) {
    val options = (5..120 step 5).toList()

    // تحديد البداية
    val initialPage = options.indexOf(currentMinutes).takeIf { it >= 0 } ?: 4

    // استخدام PagerState الجاهز (هو اللي هيظبط كل حاجة)
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { options.size }
    )

    // أول ما الصفحة تتغير وتستقر، نبعت الرقم الجديد
    LaunchedEffect(pagerState.currentPage) {
        onTimeSelected(options[pagerState.currentPage])
    }

    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // المربع الرمادي (تحديد المنتصف)
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.6f)
                .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
        )

        // استخدام VerticalPager الجاهز بدلاً من LazyColumn
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 80.dp), // بيسنتر العناصر تلقائياً
            horizontalAlignment = Alignment.CenterHorizontally
        ) { page ->
            // لو الصفحة الحالية هي اللي بتترسم، يبقى ده العنصر اللي في النص
            val isCenter = pagerState.currentPage == page

            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${options[page]}",
                    fontSize = if (isCenter) 28.sp else 20.sp,
                    fontWeight = if (isCenter) FontWeight.Bold else FontWeight.Medium,
                    color = if (isCenter) PrimaryGreen else Color.Gray,
                    modifier = Modifier.alpha(if (isCenter) 1f else 0.5f)
                )
            }
        }
    }
}