package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.TeamDTO
import com.kitching.data.firebase.COLLECTION_TEAM
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_INVITE_CODE
import kotlinx.coroutines.tasks.await

class TeamDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    TeamDataSource {
    override suspend fun getTeam(teamId: String) =
        db.collection(COLLECTION_TEAM).document(teamId).get().await()
            .toObject(TeamDTO::class.java)

    override suspend fun getTeamList(teamId: String): List<TeamDTO> =
        db.collection(COLLECTION_TEAM).whereEqualTo(DOCUMENT_ID, teamId).get().await()
            .toObjects(TeamDTO::class.java)

    override suspend fun getTeamByInviteCode(inviteCode: String): TeamDTO? =
        db.collection(COLLECTION_TEAM).whereEqualTo(DOCUMENT_INVITE_CODE, inviteCode).get().await()
            .documents.firstOrNull()?.toObject(TeamDTO::class.java)
}