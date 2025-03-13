package com.kitching.data.datasource

import com.kitching.data.dto.UserDTO
import com.kitching.domain.entities.Schedule

interface ScheduleDataSource {
    suspend fun getUserById(userId: String): UserDTO

    suspend fun getMySchedules(userId: String, teamId: String): List<Schedule>

    suspend fun getScheduleByDate(teamId: String, date: String): List<Schedule>
}