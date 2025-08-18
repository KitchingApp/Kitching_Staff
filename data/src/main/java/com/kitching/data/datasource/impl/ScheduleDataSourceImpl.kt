package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.core.exception.ExceptionHandler
import com.kitching.core.exception.KitchingRuntimeException
import com.kitching.data.datasource.ScheduleDataSource
import com.kitching.data.dto.ScheduleDTO
import com.kitching.data.dto.ScheduleTimeDTO
import com.kitching.data.dto.UserDTO
import com.kitching.data.firebase.COLLECTION_SCHEDULE
import com.kitching.data.firebase.COLLECTION_SCHEDULE_TIME
import com.kitching.data.firebase.COLLECTION_USER
import com.kitching.data.firebase.DOCUMENT_DATE
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_SCHEDULE_FIX
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import com.kitching.data.firebase.DOCUMENT_USER_ID
import kotlinx.coroutines.tasks.await

class ScheduleDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    ScheduleDataSource {
    override suspend fun getUserById(userId: String): UserDTO = ExceptionHandler.safeCall {
        val userDto = db.collection(COLLECTION_USER)
            .whereEqualTo(DOCUMENT_ID, userId)
            .get()
            .await()
            .toObjects(UserDTO::class.java)

        userDto.first() ?: throw KitchingRuntimeException.UserNotFoundException()
    }

    override suspend fun getMySchedules(
        userId: String,
        teamId: String,
    ): List<ScheduleDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_SCHEDULE)
            .whereEqualTo(DOCUMENT_USER_ID, userId)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_SCHEDULE_FIX, true)
            .get()
            .await()
            .toObjects(ScheduleDTO::class.java)
    }

    override suspend fun getScheduleByDate(
        teamId: String,
        date: String,
    ): List<ScheduleDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_SCHEDULE)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_DATE, date)
            .get()
            .await()
            .toObjects(ScheduleDTO::class.java)
    }

    override suspend fun getScheduleTimes(teamId: String): List<ScheduleTimeDTO>  = ExceptionHandler.safeCall {
            db.collection(COLLECTION_SCHEDULE_TIME).whereEqualTo(DOCUMENT_TEAM_ID, teamId).get().await()
                .toObjects(ScheduleTimeDTO::class.java)
        }

    override suspend fun createApplySchedule(scheduleDTO: ScheduleDTO) = ExceptionHandler.safeCall {
        val newSchedule = db.collection(COLLECTION_SCHEDULE).add(scheduleDTO).await().apply {
            update(DOCUMENT_ID, id).await()
        }

        val createdSchedule = newSchedule.get().await()

        if (createdSchedule.exists()) {
            true
        } else {
            throw KitchingRuntimeException.ScheduleCreateFailedException()
        }
    }
}