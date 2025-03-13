package com.kitching.data.datasource

import com.kitching.domain.entities.Schedule
import java.time.LocalDate

interface ScheduleDataSource {
    suspend fun getMySchedules(userId: String, teamId: String): List<Schedule>

    suspend fun getScheduleByDate(teamId: String, date: String): List<Schedule>
}