package com.kitching.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.ScheduleDTO
import com.kitching.data.dto.ScheduleTimeDTO
import com.kitching.data.firebase.COLLECTION_SCHEDULE
import com.kitching.data.firebase.COLLECTION_SCHEDULE_TIME
import com.kitching.domain.entities.Schedule
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class ScheduleDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    ScheduleDataSource {
    override suspend fun getMySchedules(
        userId: String,
        teamId: String,
    ): List<Schedule> {
        val schedules = db.collection(COLLECTION_SCHEDULE)
            .whereEqualTo("userId", userId)
            .whereEqualTo("teamId", teamId)
            .whereEqualTo("fix", true)
            .get()
            .await()
            .toObjects(ScheduleDTO::class.java)

        val scheduleTimes = db.collection(COLLECTION_SCHEDULE_TIME)
            .whereEqualTo("teamId", teamId)
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
            .whereEqualTo("teamId", teamId)
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(ScheduleDTO::class.java)

        val scheduleTimes = db.collection(COLLECTION_SCHEDULE_TIME)
            .whereEqualTo("teamId", teamId)
            .get()
            .await()
            .toObjects(ScheduleTimeDTO::class.java)

        return schedules.map { scheduleDTO ->
            val scheduleTime = scheduleTimes.find { it.id == scheduleDTO.id }

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
}