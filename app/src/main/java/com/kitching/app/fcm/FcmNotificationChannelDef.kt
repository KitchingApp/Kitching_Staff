package com.kitching.app.fcm

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import com.kitching.app.R
import com.kitching.app.common.NotificationChannelDef
import com.kitching.core.common.navigation.ScreenRouteDef


/**
 * Schedule rejected notification channel스케줄 신청 반려 관련 채널 정의
 *
 */
class ScheduleRejectedNotificationChannel() : NotificationChannelDef(
    channelId = "SCHEDULE_REJECTED",
    importance = NotificationManager.IMPORTANCE_DEFAULT,
    channelName = R.string.notification_channel_schedule_rejected_name,
    channelDescription = R.string.notification_channel_schedule_rejected_description
) {
    /**
     * Show schedule reject notification 받은편지함 스타일(여러 텍스트 줄)의 스케줄 반려 노티피케이션을 생성
     *
     * @param teamName
     * @param scheduleDate
     * @param scheduleTimeName
     * @param rejectReason
     */
    fun showScheduleRejectNotification(
        context: Context,
        teamName: String,
        scheduleDate: String,
        scheduleTimeName: String,
        rejectReason: String
    ) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = 2001

        val notificationContentText =
            context.getString(
                R.string.notification_schedule_rejected_content_text,
                teamName,
                scheduleDate,
                scheduleTimeName
            )
        val notification = createBasicNotificationBuilder(
            context,
            context.getString(R.string.notification_schedule_rejected_content_title),
            notificationContentText,
            ScreenRouteDef.BottomTab.ScheduleGraph
        )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        context.getString(
                            R.string.notification_schedule_rejected_content_big_text,
                            teamName,
                            scheduleDate,
                            scheduleTimeName,
                            rejectReason
                        )
                    )
            )
            .build()

        notificationManager.notify(notificationId, notification)
    }
}

class NoticeNotificationChannel : NotificationChannelDef(
    channelId = "NOTICE",
    importance = NotificationManager.IMPORTANCE_DEFAULT,
    channelName = R.string.notification_channel_notice_name,
    channelDescription = R.string.notification_channel_notice_description
) {
    fun showNoticeNotification(
        context: Context,
        title: String,
        writerName: String,
        content: String
    ) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = System.currentTimeMillis().toInt()

        val notificationTitle = "$title | $writerName"

        val shortContent = if (content.length > 20) {
            content.take(20) + "..."
        } else {
            content
        }

        val notificationBody = shortContent

        // 확장된 내용 (BigTextStyle용)
        val expandedContent = """
            $title
            
            작성자: $writerName
            
            $content
        """.trimIndent()

        val notification = createBasicNotificationBuilder(
            context = context,
            title = notificationTitle,
            text = notificationBody,
            targetRoute = ScreenRouteDef.Other.Notice
        )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(expandedContent)
                    .setBigContentTitle(notificationTitle)
                    .setSummaryText("새 공지사항")
            ).build()

        notificationManager.notify(notificationId, notification)
    }
}