package com.kitching.data.datasource

import com.kitching.domain.entities.Schedule

interface ScheduleDataSource {
    suspend fun getSchedules(userId: String, teamId: String): List<Schedule>
}