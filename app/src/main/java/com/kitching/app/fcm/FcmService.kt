package com.kitching.app.fcm

import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kitching.data.PreferencesDataSource
import com.kitching.data.repository.FcmTokenRepositoryImpl
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            PreferencesDataSource(this@FcmService).getUserId().collectLatest { userId ->
                if (userId is AppResult.Success) {
                    if (userId.data !== "") {
                        FcmTokenRepositoryImpl().updateToken(
                            userId = userId.data,
                            token = token,
                            deviceModel = Build.MODEL
                        ).takeWhile { result -> !(result is AppResult.Success && result.data) }
                            .launchIn(this@launch)
                    }
                }
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val messageData = message.data

        val teamName = messageData["teamName"] ?: throw Throwable("teamName is null")
        val scheduleDate = messageData["scheduleDate"] ?: throw Throwable("scheduleDate is null")
        val scheduleTimeName =
            messageData["scheduleTimeName"] ?: throw Throwable("scheduleTimeName is null")
        val rejectReason = messageData["rejectReason"] ?: throw Throwable("rejectReason is null")

        val title = "${teamName}의 $scheduleDate $scheduleTimeName 스케줄 신청이 반려되었습니다."
        val body = "반려 사유: $rejectReason"

        ScheduleRejectedNotificationChannel().showScheduleRejectNotification(
            context = this,
            teamName = teamName,
            scheduleDate = scheduleDate,
            scheduleTimeName = scheduleTimeName,
            rejectReason = rejectReason
        )
    }
}