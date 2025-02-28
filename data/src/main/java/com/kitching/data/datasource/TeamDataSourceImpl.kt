package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.TeamDTO
import com.kitching.data.firebase.COLLECTION_TEAM
import kotlinx.coroutines.tasks.await

class TeamDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    TeamDataSource {
    override suspend fun getTeam(teamId: String) =
        db.collection(COLLECTION_TEAM).document(teamId).get().await()
            .toObject(TeamDTO::class.java)

    override suspend fun createTeam(
        ownerId: String,
        inviteCode: String,
        teamName: String,
        teamAmount: Int,
    ) = db.collection(COLLECTION_TEAM).add(
        TeamDTO(
            id = "",
            inviteCode = inviteCode,
            ownerId = ownerId,
            teamName = teamName,
            teamAmount = teamAmount
        )
    ).await().apply {
        update("id", id).await()
    }.id

    override suspend fun getTeamList(teamId: String): List<TeamDTO> =
        db.collection(COLLECTION_TEAM).whereEqualTo("id", teamId).get().await()
            .toObjects(TeamDTO::class.java)
}