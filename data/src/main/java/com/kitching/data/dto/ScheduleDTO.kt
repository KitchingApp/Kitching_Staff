package com.kitching.data.dto

import com.kitching.domain.entities.Schedule

data class ScheduleDTO(
    val id: String = "",
    val teamId: String = "",
    val userId: String = "",
    val scheduleTimeId: String = "",
    val date: String = "",
    val fix: Boolean = true,
)
