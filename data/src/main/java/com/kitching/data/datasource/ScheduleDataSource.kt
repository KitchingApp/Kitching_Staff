package com.kitching.data.datasource

import com.kitching.data.dto.ScheduleDTO
import com.kitching.data.dto.ScheduleTimeDTO
import com.kitching.data.dto.UserDTO
import com.kitching.domain.entities.Schedule

interface ScheduleDataSource {
    suspend fun getUserById(userId: String): UserDTO

    suspend fun getMySchedules(userId: String, teamId: String): List<Schedule>

    suspend fun getScheduleByDate(teamId: String, date: String): List<Schedule>

    suspend fun getScheduleTimes(teamId: String): List<ScheduleTimeDTO>

    suspend fun createApplySchedule(scheduleDTO: ScheduleDTO): Boolean
}