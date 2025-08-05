package com.kitching.app.fcm

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

        Log.d("FcmService", "새로운 FCM 토큰 수신: $token")

        CoroutineScope(Dispatchers.IO).launch {
            val preferencesDataSource = PreferencesDataSource(this@FcmService)
            val userId = preferencesDataSource.getUserId()

            if (userId.isNotEmpty()) {
                FcmTokenRepositoryImpl().updateToken(
                    userId = userId,
                    token = token,
                    deviceModel = Build.MODEL
                ).collectLatest { result ->
                    when (result) {
                        is AppResult.Loading -> {}
                        else -> {
                            preferencesDataSource.saveFcmToken(token)
                        }
                    }
                }
            } else {
                preferencesDataSource.saveFcmToken(token)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val messageData = message.data
        val notificationType = messageData["type"]

        when (notificationType) {
            "notice" -> handleNoticeNotification(messageData)
            else -> handleScheduleRejectedNotification(messageData)
        }
    }

    private fun handleNoticeNotification(messageData: Map<String, String>) {
        val title = messageData["noticeTitle"] ?: throw Throwable("noticeTitle is null")
        val writerName = messageData["writerName"] ?: throw Throwable("writerName is null")
        val content = messageData["content"] ?: throw Throwable("content is null")

        NoticeNotificationChannel().showNoticeNotification(
            context = this,
            title = title,
            writerName = writerName,
            content = content
        )
    }

    private fun handleScheduleRejectedNotification(messageData: Map<String, String>) {
        val teamName = messageData["teamName"] ?: throw Throwable("teamName is null")
        val scheduleDate = messageData["scheduleDate"] ?: throw Throwable("scheduleDate is null")
        val scheduleTimeName =
            messageData["scheduleTimeName"] ?: throw Throwable("scheduleTimeName is null")
        val rejectReason = messageData["rejectReason"] ?: throw Throwable("rejectReason is null")

        ScheduleRejectedNotificationChannel().showScheduleRejectNotification(
            context = this,
            teamName = teamName,
            scheduleDate = scheduleDate,
            scheduleTimeName = scheduleTimeName,
            rejectReason = rejectReason
        )
    }
}