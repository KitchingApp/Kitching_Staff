package com.kitching.app.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.kitching.app.MainActivity
import com.kitching.app.R
import com.kitching.core.common.navigation.ScreenRouteDef
import kotlinx.serialization.json.Json

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
    companion object {
        const val EXTRA_TARGET_ROUTE = "target_route"
    }

    /** 노티피케이션 채널 생성 */
    fun createChannel(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager.getNotificationChannel(channelId) == null) {
            val channel =
                NotificationChannel(channelId, context.getString(channelName), importance).apply {
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
    fun createBasicNotificationBuilder(
        context: Context,
        title: String,
        text: String,
        targetRoute: ScreenRouteDef? = null
    ): NotificationCompat.Builder {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            targetRoute?.let { route ->
                val routeJson = Json.encodeToString(route)
                putExtra(EXTRA_TARGET_ROUTE, routeJson)
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setChannelId(channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}