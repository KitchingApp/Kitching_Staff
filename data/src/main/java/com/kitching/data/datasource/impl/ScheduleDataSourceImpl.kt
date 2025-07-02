package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.datasource.ScheduleDataSource
import com.kitching.data.dto.ScheduleDTO
import com.kitching.data.dto.ScheduleTimeDTO
import com.kitching.data.dto.UserDTO
import com.kitching.data.firebase.COLLECTION_SCHEDULE
import com.kitching.data.firebase.COLLECTION_SCHEDULE_TIME
import com.kitching.data.firebase.COLLECTION_USER
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_SCHEDULE_DATE
import com.kitching.data.firebase.DOCUMENT_SCHEDULE_FIX
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import com.kitching.data.firebase.DOCUMENT_USER_ID
import com.kitching.domain.entities.Schedule
import kotlinx.coroutines.tasks.await

class ScheduleDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    ScheduleDataSource {
    override suspend fun getUserById(userId: String): UserDTO {
        val userDto = db.collection(COLLECTION_USER)
            .whereEqualTo(DOCUMENT_ID, userId)
            .get()
            .await()
            .toObjects(UserDTO::class.java)

        return userDto.firstOrNull() ?: UserDTO()
    }

    override suspend fun getMySchedules(
        userId: String,
        teamId: String,
    ): List<Schedule> {
        val schedules = db.collection(COLLECTION_SCHEDULE)
            .whereEqualTo(DOCUMENT_USER_ID, userId)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_SCHEDULE_FIX, true)
            .get()
            .await()
            .toObjects(ScheduleDTO::class.java)

        val scheduleTimes = db.collection(COLLECTION_SCHEDULE_TIME)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .toObjects(ScheduleTimeDTO::class.java)

        return schedules.map { scheduleDTO ->
            val scheduleTime = scheduleTimes.find { it.id == scheduleDTO.scheduleTimeId }
            Schedule(
                scheduleId = scheduleDTO.id,
                userId = scheduleDTO.userId,
                userName = "",
                scheduleTimeName = scheduleTime?.name ?: "",
                date = scheduleDTO.date,
                fix = scheduleDTO.fix,
                color = scheduleTime?.color ?: "#00ffff"
            )
        }
    }

    override suspend fun getScheduleByDate(
        teamId: String,
        date: String,
    ): List<Schedule> {
        val schedules = db.collection(COLLECTION_SCHEDULE)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_SCHEDULE_DATE, date)
            .get()
            .await()
            .toObjects(ScheduleDTO::class.java)

        val scheduleTimes = db.collection(COLLECTION_SCHEDULE_TIME)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .toObjects(ScheduleTimeDTO::class.java)

        return schedules.map { scheduleDTO ->
            val scheduleTime = scheduleTimes.find { it.id == scheduleDTO.scheduleTimeId }
            val user = getUserById(scheduleDTO.userId)

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
    }

    override suspend fun getScheduleTimes(teamId: String): List<ScheduleTimeDTO>  =
        db.collection(COLLECTION_SCHEDULE_TIME).whereEqualTo(DOCUMENT_TEAM_ID, teamId).get().await()
            .toObjects(ScheduleTimeDTO::class.java)

    override suspend fun createApplySchedule(scheduleDTO: ScheduleDTO) = runCatching {
        db.collection(COLLECTION_SCHEDULE).add(
            scheduleDTO
        ).await().apply {
            this.update(DOCUMENT_ID, this.id).await()
        }
    }.isSuccess
}