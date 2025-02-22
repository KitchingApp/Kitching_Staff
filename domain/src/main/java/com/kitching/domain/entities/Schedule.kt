package com.kitching.domain.entities

data class Schedule(
    val scheduleId: String,
    val userId: String,
    val userName: String,
    val scheduleTimeName: String,
    val date: String,
    val fix: Boolean,
)
