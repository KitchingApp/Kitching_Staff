package com.kitching.data.repository

import com.kitching.data.datasource.ScheduleDataSource
import com.kitching.data.datasource.ScheduleDataSourceImpl
import com.kitching.domain.entities.Schedule
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class ScheduleRepositoryImpl(private val dataSource: ScheduleDataSource = ScheduleDataSourceImpl()) :
    ScheduleRepository {
    override fun getMySchedules(
        userId: String,
        teamId: String,
    ): Flow<AppResult<List<Schedule>>> = flow {
        emit(AppResult.Loading)
        try {
            val schedules = dataSource.getMySchedules(userId, teamId)
            emit(AppResult.Success(schedules))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }

    override fun getScheduleByDate(
        teamId: String,
        date: LocalDate,
    ): Flow<AppResult<List<Schedule>>> = flow {
        emit(AppResult.Loading)
        try {
            val schedules = dataSource.getScheduleByDate(teamId, date)
            emit(AppResult.Success(schedules))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }
}