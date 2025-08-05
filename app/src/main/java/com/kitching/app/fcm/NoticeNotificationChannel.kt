package com.kitching.app.fcm

import com.kitching.app.R
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.kitching.app.common.NotificationChannelDef

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
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
            text = notificationBody
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