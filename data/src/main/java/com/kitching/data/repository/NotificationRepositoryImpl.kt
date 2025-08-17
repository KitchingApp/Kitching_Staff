package com.kitching.data.repository

import android.content.Context
import com.kitching.data.dao.NotificationDAO
import com.kitching.data.entity.toEntity
import com.kitching.data.entity.toNoticeNotificationList
import com.kitching.data.entity.toScheduleNotificationList
import com.kitching.data.room.NotificationRoomDatabase
import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification
import com.kitching.domain.repository.NotificationRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl(context: Context) : NotificationRepository {
    private val notificationDAO: NotificationDAO

    init {
        val notificationDatabase = NotificationRoomDatabase.getDatabase(context.applicationContext)
        notificationDAO = notificationDatabase.notificationDao()
    }

    override suspend fun insertScheduleNotification(notification: ScheduleNotification) = flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.insertScheduleNotification(notification.toEntity())
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun getScheduleNotifications() = flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.getScheduleNotifications().toScheduleNotificationList()
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun deleteScheduleNotification(id: Long) = flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.deleteScheduleNotification(id)
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun deleteAllScheduleNotification() = flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.deleteAllScheduleNotification()
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun insertNoticeNotification(notification: NoticeNotification) =flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.insertNoticeNotification(notification.toEntity())
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun getNoticeNotifications() = flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.getNoticeNotifications().toNoticeNotificationList()
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun deleteNoticeNotification(id: Long) = flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.deleteNoticeNotification(id)
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun deleteAllNoticeNotification() = flow {
        emit(AppResult.Loading)

        emit(AppResult.Success(
            notificationDAO.deleteAllNoticeNotification()
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }
}