package com.kitching.data.datasource

import com.kitching.data.dto.ScheduleDTO
import com.kitching.data.dto.ScheduleTimeDTO
import com.kitching.data.dto.UserDTO

interface ScheduleDataSource {
    suspend fun getUserById(userId: String): UserDTO

    suspend fun getMySchedules(userId: String, teamId: String): List<ScheduleDTO>

    suspend fun getScheduleByDate(teamId: String, date: String): List<ScheduleDTO>

    suspend fun getScheduleTimes(teamId: String): List<ScheduleTimeDTO>

    suspend fun createApplySchedule(scheduleDTO: ScheduleDTO): Boolean
}