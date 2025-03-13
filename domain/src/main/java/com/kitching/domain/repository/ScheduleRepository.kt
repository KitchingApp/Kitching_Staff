package com.kitching.domain.repository

import com.kitching.domain.entities.Schedule
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ScheduleRepository {
    fun getMySchedules(userId: String, teamId: String): Flow<AppResult<List<Schedule>>>

    fun getScheduleByDate(teamId: String, date: String): Flow<AppResult<List<Schedule>>>
}