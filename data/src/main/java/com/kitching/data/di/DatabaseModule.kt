package com.kitching.data.di

import android.content.Context
import androidx.room.Room
import com.kitching.data.dao.NotificationDAO
import com.kitching.data.room.NotificationRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNotificationDatabase(
        @ApplicationContext context: Context
    ): NotificationRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NotificationRoomDatabase::class.java,
            "notification_database.db"
        ).build()
    }

    @Provides
    fun provideNotificationDao(database: NotificationRoomDatabase): NotificationDAO {
        return database.notificationDao()
    }
}