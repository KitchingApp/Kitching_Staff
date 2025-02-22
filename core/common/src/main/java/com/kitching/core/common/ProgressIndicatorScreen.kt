package com.kitching.core.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.theme.Caption1_m
import com.kitching.core.designsystem.theme.H1
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray100
import com.kitching.core.designsystem.theme.PrimaryGreen300

@Composable
fun ProgressIndicatorScreen() {
    val indicatorItem = ProgressIndicatorItem.getRandomItem()

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier.width(100.dp),
                        model = indicatorItem.image,
                        contentDescription = null
                    )
                    Text(
                        text = indicatorItem.title,
                        style = H1.copy(color = PrimaryGreen300)
                    )
                }
                LinearProgressIndicator(
                    modifier = Modifier.width(240.dp).height(16.dp),
                    trackColor = NeutralGray100,
                    color = PrimaryGreen300
                )
                Text(
                    text = "요리가 완성되는 중입니다. 조금만 기다려주세요!",
                    style = Caption1_m.copy(color = NeutralGray0)
                )
            }
        }
    }
}