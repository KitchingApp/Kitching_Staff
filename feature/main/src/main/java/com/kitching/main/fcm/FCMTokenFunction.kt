package com.kitching.main.fcm

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

const val FCM_TAG = "FCM"

/**
 * Firebase massaging token handler FCM 토큰을 가져오는 함수
 *
 */
fun getFcmToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(FCM_TAG, "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token = task.result

        // Log and toast
        Log.d(FCM_TAG, token)
    })
}