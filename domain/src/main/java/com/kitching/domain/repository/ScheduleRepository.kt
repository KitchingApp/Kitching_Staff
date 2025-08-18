package com.kitching.domain.repository

import com.kitching.domain.entities.Schedule
import com.kitching.domain.entities.ScheduleTime
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getMySchedules(userId: String, teamId: String): Flow<AppResult<List<Schedule>>>

    suspend fun getScheduleByDate(teamId: String, date: String): Flow<AppResult<List<Schedule>>>

    suspend fun getScheduleTimes(teamId: String): Flow<AppResult<List<ScheduleTime>>>

    suspend fun createApplySchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean
    ): Flow<AppResult<Boolean>>
}