package com.kitching.domain.repository

import com.kitching.domain.entities.Schedule
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSchedules(userId: String, teamId: String): Flow<AppResult<List<Schedule>>>

}