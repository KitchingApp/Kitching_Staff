package com.kitching.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KitchingStaffTheme {
                AppNavHost()
            }
        }
    }
}