package com.kitching.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_notification_tbl")
data class ScheduleNotificationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "schedule_date")
    val scheduleDate: String,

    @ColumnInfo(name = "schedule_time_name")
    val scheduleTimeName: String,

    @ColumnInfo(name = "reject_reason")
    val rejectReason: String,
)
