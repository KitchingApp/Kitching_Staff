package com.kitching.core.common.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kitching.core.common.R

@Composable
fun SplashScreen(
    goLogin: () -> Unit,
    goMain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(modifier = Modifier
            .padding(top = 360.dp)
            .sizeIn(150.dp, 80.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.kitching_splash),
            contentDescription = "splashImg"
        )

        Button(onClick = goLogin) {
            Text("로그인")
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = goMain) {
            Text("메인")
        }
    }
}