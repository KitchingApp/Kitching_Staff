package com.kitching.app.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import com.kitching.app.R

/**
 * Notification channel def FCM 노티피케이션 채널을 정의하는 sealed class
 *
 * @property channelId 채널 아이디
 * @property importance 채널 중요도(ex. NotificationManager.IMPORTANCE_DEFAULT)
 * @property channelName 채널 이름(resource)
 * @property channelDescription 채널 설명(resource)
 * @constructor Create empty Notification channel def
 */
open class NotificationChannelDef(
    private val channelId: String,
    private val importance: Int,
    private val channelName: Int,
    private val channelDescription: Int
) {

    /** 노티피케이션 채널 생성 */
    fun createChannel(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(channelId, context.getString(channelName), importance).apply {
                description = context.getString(channelDescription)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Create basic notification builder 기본스타일 알림 빌더 만들기
     *
     * smallIcon, channelId, contentTitle, contentText가 빌드 된 상태를 반환
     *
     * @param context
     * @param title
     * @param text
     * @return
     */
    fun createBasicNotificationBuilder(context: Context, title: String, text: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setChannelId(channelId)
            .setContentTitle(title)
            .setContentText(text)
    }
}