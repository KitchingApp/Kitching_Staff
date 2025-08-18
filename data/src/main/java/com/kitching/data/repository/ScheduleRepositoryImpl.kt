package com.kitching.data.repository

import com.kitching.data.datasource.ScheduleDataSource
import com.kitching.data.datasource.impl.ScheduleDataSourceImpl
import com.kitching.data.dto.ScheduleDTO
import com.kitching.domain.entities.Schedule
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ScheduleRepositoryImpl(private val dataSource: ScheduleDataSource = ScheduleDataSourceImpl()) :
    ScheduleRepository {
    override suspend fun getMySchedules(
        userId: String,
        teamId: String,
    ) = flow {
        emit(AppResult.Loading)

        val schedules = dataSource.getMySchedules(userId, teamId)
        val scheduleTimes = dataSource.getScheduleTimes(teamId)

        val domainSchedules = schedules.map { scheduleDTO ->
            val scheduleTime = scheduleTimes.find { it.id == scheduleDTO.scheduleTimeId }

            Schedule(
                scheduleId = scheduleDTO.id,
                userId = scheduleDTO.userId,
                userName = "",
                scheduleTimeName = scheduleTime?.name ?: "",
                date = scheduleDTO.date,
                fix = scheduleDTO.fix,
                color = scheduleTime?.color ?: "00ffff"
            )
        }

        emit(AppResult.Success(domainSchedules))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun getScheduleByDate(
        teamId: String,
        date: String,
    ) = flow {
        emit(AppResult.Loading)

        val schedules = dataSource.getScheduleByDate(teamId, date)
        val scheduleTimes = dataSource.getScheduleTimes(teamId)

        val domainSchedules = schedules.map { scheduleDTO ->
            val scheduleTime = scheduleTimes.find { it.id == scheduleDTO.scheduleTimeId }
            val user = dataSource.getUserById(scheduleDTO.userId)

            Schedule(
                scheduleId = scheduleDTO.id,
                userId = scheduleDTO.userId,
                userName = user.userName,
                scheduleTimeName = scheduleTime?.name ?: "",
                date = scheduleDTO.date,
                fix = scheduleDTO.fix,
                color = scheduleTime?.color ?: "#00ffff"
            )
        }

        emit(AppResult.Success(domainSchedules))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun getScheduleTimes(teamId: String) = flow {
            emit(AppResult.Loading)

            val scheduleTimes = dataSource.getScheduleTimes(teamId)

            emit(AppResult.Success(scheduleTimes.map { it.toDomain() }))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override suspend fun createApplySchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean
    ) = flow {
        emit(AppResult.Loading)

        val schedule = ScheduleDTO(
            teamId = teamId,
            userId = userId,
            scheduleTimeId = scheduleTimeId,
            date = dateString,
            fix = fix
        )

        val result = dataSource.createApplySchedule(schedule)

        emit(AppResult.Success(result))
    }.catch {
        emit(AppResult.Failure(it))
    }
}