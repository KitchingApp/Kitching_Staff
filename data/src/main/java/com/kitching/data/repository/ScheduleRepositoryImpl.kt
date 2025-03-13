package com.kitching.data.repository

import com.kitching.data.datasource.ScheduleDataSource
import com.kitching.data.datasource.ScheduleDataSourceImpl
import com.kitching.data.dto.ScheduleDTO
import com.kitching.domain.entities.Schedule
import com.kitching.domain.entities.ScheduleTime
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        date: String,
    ): Flow<AppResult<List<Schedule>>> = flow {
        emit(AppResult.Loading)
        try {
            val schedules = dataSource.getScheduleByDate(teamId, date)
            emit(AppResult.Success(schedules))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }

    override fun getScheduleTimes(teamId: String): Flow<AppResult<List<ScheduleTime>>> =
        flow {
            emit(AppResult.Loading)
            val scheduleTimes = dataSource.getScheduleTimes(teamId)
            if (scheduleTimes.isEmpty()) emit(AppResult.Success(emptyList()))
            else emit(AppResult.Success(scheduleTimes.map {
                ScheduleTime(
                    scheduleTimeId = it.id,
                    scheduleTimeName = it.name,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    color = it.color
                )
            }))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override fun createApplySchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean
    ): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)
        emit(
            AppResult.Success(
                dataSource.createApplySchedule(
                    ScheduleDTO(
                        id = "",
                        teamId = teamId,
                        userId = userId,
                        scheduleTimeId = scheduleTimeId,
                        date = dateString,
                        fix = fix
                    )
                )
            )
        )
    }.catch {
        emit(AppResult.Failure(it))
    }
}