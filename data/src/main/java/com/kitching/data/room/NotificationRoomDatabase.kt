package com.kitching.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kitching.data.dao.NotificationDAO
import com.kitching.data.entity.NoticeNotificationEntity
import com.kitching.data.entity.ScheduleNotificationEntity

@Database(entities = [ScheduleNotificationEntity::class, NoticeNotificationEntity::class], version = 1, exportSchema = false)
abstract class NotificationRoomDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDAO
}