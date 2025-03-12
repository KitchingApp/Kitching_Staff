package com.kitching.data.datasource

import com.kitching.domain.entities.Schedule

interface ScheduleDataSource {
    suspend fun getMySchedules(userId: String, teamId: String): List<Schedule>
}