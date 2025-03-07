package com.kitching.main.fcm

import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kitching.data.PreferencesDataSource
import com.kitching.data.repository.FcmTokenRepositoryImpl
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            PreferencesDataSource(this@FcmService).getUserId().collectLatest { userId ->
                if(userId is AppResult.Success) {
                    FcmTokenRepositoryImpl().updateToken(
                        userId = userId.data,
                        token = token,
                        deviceModel = Build.MODEL
                    )
                }
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("$FCM_TAG - onMessageReceived", remoteMessage.toString())
        // 푸시 메시지가 도착하면 처리하는 코드
        remoteMessage.notification?.let {
            Log.d("FCM", "메시지 수신: ${it.body}")
        }
    }
}
