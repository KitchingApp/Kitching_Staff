package com.kitching.domain.repository

import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun insertScheduleNotification(notification: ScheduleNotification): Flow<AppResult<Unit>>
    suspend fun getScheduleNotifications(): Flow<AppResult<List<ScheduleNotification>>>
    suspend fun deleteScheduleNotification(id: Long): Flow<AppResult<Unit>>
    suspend fun deleteAllScheduleNotification(): Flow<AppResult<Unit>>

    suspend fun insertNoticeNotification(notification: NoticeNotification): Flow<AppResult<Unit>>
    suspend fun getNoticeNotifications(): Flow<AppResult<List<NoticeNotification>>>
    suspend fun deleteNoticeNotification(id: Long): Flow<AppResult<Unit>>
    suspend fun deleteAllNoticeNotification(): Flow<AppResult<Unit>>
}