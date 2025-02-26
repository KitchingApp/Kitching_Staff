package com.kitching.data.dto

import com.kitching.domain.entities.ScheduleTime

data class ScheduleTimeDTO(
    val id: String = "",
    val teamId: String = "",
    val name: String = "",
    val startTime: String = "",
    val endTime: String = "",
) {
    fun toDomain(): ScheduleTime {
        return ScheduleTime(
            scheduleTimeId = id,
            scheduleTimeName = name,
            startTime = startTime,
            endTime = endTime
        )
    }
}
