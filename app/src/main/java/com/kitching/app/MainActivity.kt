package com.kitching.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kitching.app.common.askNotificationPermission
import com.kitching.app.navigation.AppNavHost
import com.kitching.core.designsystem.KitchingStaffTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askNotificationPermission()

        setContent {
            KitchingStaffTheme {
                AppNavHost(intent)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        recreate()
    }
}