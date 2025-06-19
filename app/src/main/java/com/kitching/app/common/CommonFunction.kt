package com.kitching.app.common

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.kitching.core.common.ProgressIndicatorScreen
import com.kitching.domain.util.AppResult

fun showToast(message: String, delayTime: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(KitchingApplication.getInstance(), message, delayTime).show()
}

/**
 * 알림 권한을 체크하고 허용되지 않았을 경우 요청
 *
 */
fun ComponentActivity.askNotificationPermission() {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    showToast("알림 권한이 허용되었습니다.")
                } else {
                    showToast("알림 권한이 거부되었습니다. 설정에서 허용해주세요.")
                }
            }.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}