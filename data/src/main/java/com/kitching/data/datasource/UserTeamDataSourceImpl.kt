package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.UserTeamDTO
import com.kitching.data.firebase.COLLECTION_USER_TEAM
import kotlinx.coroutines.tasks.await

class UserTeamDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    UserTeamDataSource {
    override suspend fun getAllMembers(teamId: String): List<UserTeamDTO> =
        db.collection(COLLECTION_USER_TEAM).whereEqualTo("teamId", teamId).get().await()
            .toObjects(UserTeamDTO::class.java)

    override suspend fun getMember(teamId: String, userId: String): UserTeamDTO? {
        val userTeam = db.collection(COLLECTION_USER_TEAM).whereEqualTo("teamId", teamId)
            .whereEqualTo("userId", userId).get().await()

        return if (userTeam.isEmpty) null
        else userTeam.documents.first().toObject(UserTeamDTO::class.java)
    }

    override suspend fun getUserTeams(userId: String): List<UserTeamDTO> =
        db.collection(COLLECTION_USER_TEAM).whereEqualTo("userId", userId).get().await()
            .toObjects(UserTeamDTO::class.java)

    override suspend fun createUserTeams(
        userId: String,
        teamId: String,
        staffLevelId: String,
        manager: Boolean
    ) = runCatching {
        db.collection(COLLECTION_USER_TEAM).add(
            UserTeamDTO(
                id = "",
                userId = userId,
                teamId = teamId,
                manager = manager,
                staffLevelId = staffLevelId
            )
        ).await().apply {
            this.update("id", id).await()
        }
    }.isSuccess

    override suspend fun updateMember(
        userTeamId: String,
        staffLevelId: String,
        manager: Boolean
    ) = runCatching {
        db.collection(COLLECTION_USER_TEAM).document(userTeamId)
            .update("staffLevelId", staffLevelId, "manager", manager)
    }.isSuccess
}