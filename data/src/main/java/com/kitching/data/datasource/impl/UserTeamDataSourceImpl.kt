package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.datasource.UserTeamDataSource
import com.kitching.data.dto.StaffLevelDTO
import com.kitching.data.dto.UserDTO
import com.kitching.data.dto.UserTeamDTO
import com.kitching.data.firebase.COLLECTION_USER
import com.kitching.data.firebase.COLLECTION_USER_TEAM
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import com.kitching.data.firebase.DOCUMENT_USER_ID
import kotlinx.coroutines.tasks.await

class UserTeamDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    UserTeamDataSource {
    override suspend fun getAllMembers(teamId: String): List<UserTeamDTO> =
        db.collection(COLLECTION_USER_TEAM).whereEqualTo(DOCUMENT_TEAM_ID, teamId).get().await()
            .toObjects(UserTeamDTO::class.java)

    override suspend fun getMember(teamId: String, userId: String): UserTeamDTO? {
        val userTeam = db.collection(COLLECTION_USER_TEAM).whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_USER_ID, userId).get().await()

        return if (userTeam.isEmpty) null
        else userTeam.documents.first().toObject(UserTeamDTO::class.java)
    }

    override suspend fun getUserTeams(userId: String): List<UserTeamDTO> =
        db.collection(COLLECTION_USER_TEAM).whereEqualTo(DOCUMENT_USER_ID, userId).get().await()
            .toObjects(UserTeamDTO::class.java)

    override suspend fun createUserTeam(
        userId: String,
        teamId: String,
    ) = runCatching {
        db.collection(COLLECTION_USER_TEAM).add(
            UserTeamDTO(
                id = "",
                teamId = teamId,
                userId = userId,
                staffLevelId = "",
                manager = false
            )
        ).await().apply {
            this.update(DOCUMENT_ID, id).await()
        }
    }.isSuccess

    override suspend fun getUser(userId: String): UserDTO? =
        db.collection(COLLECTION_USER).document(userId).get().await()
            .toObject(UserDTO::class.java)

    override suspend fun getStaffLevel(staffLevelId: String): StaffLevelDTO? =
        db.collection(COLLECTION_USER).document(staffLevelId).get().await()
            .toObject(StaffLevelDTO::class.java)

}