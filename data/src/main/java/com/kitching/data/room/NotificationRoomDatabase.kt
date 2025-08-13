package com.kitching.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kitching.data.dao.NotificationDAO
import com.kitching.data.entity.NoticeNotificationEntity
import com.kitching.data.entity.ScheduleNotificationEntity

@Database(entities = [ScheduleNotificationEntity::class, NoticeNotificationEntity::class], version = 1, exportSchema = false)
abstract class NotificationRoomDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDAO

    companion object {
        private lateinit var INSTANCE: NotificationRoomDatabase
        internal fun getDatabase(context: Context): NotificationRoomDatabase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(NotificationRoomDatabase::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            NotificationRoomDatabase::class.java,
                            "notification_database.db"
                        ).build()
                }
            }
            return INSTANCE
        }
    }
}