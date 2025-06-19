package com.kitching.main.view.schedule.tab

import com.kitching.main.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyScheduleScreen(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 0.dp, 33.dp),
            imageVector = ImageVector.vectorResource(R.drawable.img_group),
            contentDescription = "empty schedule icon",
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = text,
            color = Color.LightGray,
            fontSize = 20.dp.value.sp,
            textAlign = TextAlign.Center
        )
    }
}