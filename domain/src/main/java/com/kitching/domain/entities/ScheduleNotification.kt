package com.kitching.domain.entities

data class ScheduleNotification(
    val id: Long = 0L,
    val scheduleDate: String,
    val scheduleTimeName: String,
    val rejectReason: String,
)
