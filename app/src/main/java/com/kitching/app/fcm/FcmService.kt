package com.kitching.app.fcm

import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification
import com.kitching.domain.repository.FcmTokenRepository
import com.kitching.domain.repository.NotificationRepository
import com.kitching.domain.util.AppResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FcmService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var preferencesDataSource: PreferencesDataSource

    @Inject
    lateinit var fcmTokenRepository: FcmTokenRepository

    private val roomIOScope = CoroutineScope(Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        roomIOScope.launch {
            val userId = preferencesDataSource.getUserId()

            if (userId.isNotEmpty()) {
                fcmTokenRepository.updateToken(
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
        val notificationType = NotificationType.fromString(messageData["type"])

        when (notificationType) {
            NotificationType.NOTICE -> handleNoticeNotification(messageData)
            NotificationType.SCHEDULE_REJECTED -> handleScheduleRejectedNotification(messageData)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopSelf()

        if (roomIOScope.isActive) {
            roomIOScope.cancel()
        }
    }

    private fun handleNoticeNotification(messageData: Map<String, String>) {
        val title = messageData["noticeTitle"] ?: throw Throwable("noticeTitle is null")
        val writerName = messageData["writerName"] ?: throw Throwable("writerName is null")
        val content = messageData["content"] ?: throw Throwable("content is null")

        roomIOScope.launch {
            val noticeNotification = NoticeNotification(
                title = title,
                writerName = writerName,
            )
            notificationRepository.insertNoticeNotification(noticeNotification).collectLatest { result ->
                when (result) {
                    is AppResult.Failure -> {
                        NoticeNotificationChannel().showNoticeNotification(
                            context = this@FcmService,
                            title = title,
                            writerName = writerName,
                            content = content
                        )
                    }

                    is AppResult.Success -> {
                        NoticeNotificationChannel().showNoticeNotification(
                            context = this@FcmService,
                            title = title,
                            writerName = writerName,
                            content = content
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun handleScheduleRejectedNotification(messageData: Map<String, String>) {
        val teamName = messageData["teamName"] ?: throw Throwable("teamName is null")
        val scheduleDate = messageData["scheduleDate"] ?: throw Throwable("scheduleDate is null")
        val scheduleTimeName =
            messageData["scheduleTimeName"] ?: throw Throwable("scheduleTimeName is null")
        val rejectReason = messageData["rejectReason"] ?: throw Throwable("rejectReason is null")

        roomIOScope.launch {
            val scheduleNotification = ScheduleNotification(
                scheduleDate = scheduleDate,
                scheduleTimeName = scheduleTimeName,
                rejectReason = rejectReason
            )

            notificationRepository.insertScheduleNotification(scheduleNotification).collectLatest { result ->
                when (result) {
                    is AppResult.Failure -> {
                        ScheduleRejectedNotificationChannel().showScheduleRejectNotification(
                            context = this@FcmService,
                            teamName = teamName,
                            scheduleDate = scheduleDate,
                            scheduleTimeName = scheduleTimeName,
                            rejectReason = rejectReason
                        )
                    }

                    is AppResult.Success -> {
                        ScheduleRejectedNotificationChannel().showScheduleRejectNotification(
                            context = this@FcmService,
                            teamName = teamName,
                            scheduleDate = scheduleDate,
                            scheduleTimeName = scheduleTimeName,
                            rejectReason = rejectReason
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

enum class NotificationType(val type: String) {
    NOTICE("notice"),
    SCHEDULE_REJECTED("schedule_rejected");

    companion object {
        fun fromString(type: String?): NotificationType {
            return entries.find { it.type == type }!!
        }
    }
}