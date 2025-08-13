package com.kitching.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kitching.data.entity.NoticeNotificationEntity
import com.kitching.data.entity.ScheduleNotificationEntity

@Dao
interface NotificationDAO {
    // 스케쥴 알림 DAO
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduleNotification(notification: ScheduleNotificationEntity)

    @Query("SELECT * FROM schedule_notification_tbl ORDER BY id DESC")
    suspend fun getScheduleNotifications(): List<ScheduleNotificationEntity>

    @Query("DELETE FROM schedule_notification_tbl WHERE id = :id")
    suspend fun deleteScheduleNotification(id: Long)

    @Query("DELETE FROM schedule_notification_tbl")
    suspend fun deleteAllScheduleNotification()

    // 공지사항 알림 DAO
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoticeNotification(notification: NoticeNotificationEntity)

    @Query("SELECT * FROM notice_notification_tbl ORDER BY id DESC")
    suspend fun getNoticeNotifications(): List<NoticeNotificationEntity>

    @Query("DELETE FROM notice_notification_tbl WHERE id = :id")
    suspend fun deleteNoticeNotification(id: Long)

    @Query("DELETE FROM notice_notification_tbl")
    suspend fun deleteAllNoticeNotification()
}