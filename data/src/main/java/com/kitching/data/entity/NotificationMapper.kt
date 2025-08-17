package com.kitching.data.entity

import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification

fun ScheduleNotification.toEntity(): ScheduleNotificationEntity = ScheduleNotificationEntity(
    id = this.id,
    scheduleDate = this.scheduleDate,
    scheduleTimeName = this.scheduleTimeName,
    rejectReason = this.rejectReason
)

fun ScheduleNotificationEntity.toDomain(): ScheduleNotification = ScheduleNotification(
    id = this.id,
    scheduleDate = this.scheduleDate,
    scheduleTimeName = this.scheduleTimeName,
    rejectReason = this.rejectReason
)

fun List<ScheduleNotificationEntity>.toScheduleNotificationList(): List<ScheduleNotification> = this.map { it.toDomain() }

fun NoticeNotification.toEntity(): NoticeNotificationEntity = NoticeNotificationEntity(
    id = this.id,
    title = this.title,
    writerName = this.writerName
)

fun NoticeNotificationEntity.toDomain(): NoticeNotification = NoticeNotification(
    id = this.id,
    title = this.title,
    writerName = this.writerName
)

fun List<NoticeNotificationEntity>.toNoticeNotificationList(): List<NoticeNotification> = this.map { it.toDomain() }