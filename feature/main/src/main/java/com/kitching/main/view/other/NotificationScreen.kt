package com.kitching.main.view.other

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kitching.core.designsystem.KitchingStaffTheme

@Composable
fun NotificationScreen() {
    KitchingStaffTheme {
        Column {
            Text(
                text = "알람 화면"
            )
        }
    }
}