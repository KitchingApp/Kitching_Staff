package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.core.exception.ExceptionHandler
import com.kitching.core.exception.KitchingRuntimeException
import com.kitching.data.datasource.UserTeamDataSource
import com.kitching.data.dto.StaffLevelDTO
import com.kitching.data.dto.UserDTO
import com.kitching.data.dto.UserTeamDTO
import com.kitching.data.firebase.COLLECTION_STAFF_LEVEL
import com.kitching.data.firebase.COLLECTION_USER
import com.kitching.data.firebase.COLLECTION_USER_TEAM
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import com.kitching.data.firebase.DOCUMENT_USER_ID
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserTeamDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserTeamDataSource {
    override suspend fun getAllMembers(teamId: String): List<UserTeamDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_USER_TEAM).whereEqualTo(DOCUMENT_TEAM_ID, teamId).get().await()
            .toObjects(UserTeamDTO::class.java)
    }

    override suspend fun getMember(teamId: String, userId: String): UserTeamDTO? = ExceptionHandler.safeCall {
        val userTeam = db.collection(COLLECTION_USER_TEAM).whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_USER_ID, userId).get().await()

        if (userTeam.isEmpty) {
            null
        } else {
            userTeam.documents.first().toObject(UserTeamDTO::class.java)
        }
    }

    override suspend fun getUserTeams(userId: String): List<UserTeamDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_USER_TEAM).whereEqualTo(DOCUMENT_USER_ID, userId).get().await()
            .toObjects(UserTeamDTO::class.java)
    }

    override suspend fun createUserTeam(
        userId: String,
        teamId: String,
    ) = ExceptionHandler.safeCall {
        val existingMember = getMember(teamId, userId)

        if (existingMember != null) {
            throw KitchingRuntimeException.UserTeamAlreadyExistsException()
        }

        val userTeamDTO = UserTeamDTO(
            id = "",
            teamId = teamId,
            userId = userId,
            staffLevelId = "",
            manager = false
        )

        val createUserTeam = db.collection(COLLECTION_USER_TEAM).add(userTeamDTO).await().apply {
            update(DOCUMENT_ID, id).await()
        }

        val result = createUserTeam.get().await()

        if (result.exists()) {
            true
        } else {
            throw KitchingRuntimeException.UserTeamCreateFailedException()
        }
    }

    override suspend fun getUser(userId: String): UserDTO? = ExceptionHandler.safeCall {
        db.collection(COLLECTION_USER).document(userId).get().await()
            .toObject(UserDTO::class.java)
    }

    override suspend fun getStaffLevel(staffLevelId: String): StaffLevelDTO? = ExceptionHandler.safeCall {
        db.collection(COLLECTION_STAFF_LEVEL).document(staffLevelId).get().await()
            .toObject(StaffLevelDTO::class.java)
    }
}