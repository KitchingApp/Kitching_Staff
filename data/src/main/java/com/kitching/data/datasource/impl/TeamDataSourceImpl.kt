package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.datasource.TeamDataSource
import com.kitching.data.dto.CommentDTO
import com.kitching.data.dto.NoticeDTO
import com.kitching.data.dto.TeamDTO
import com.kitching.data.firebase.COLLECTION_COMMENTS
import com.kitching.data.firebase.COLLECTION_NOTICE
import com.kitching.data.firebase.COLLECTION_TEAM
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_INVITE_CODE
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import com.kitching.domain.entities.User
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

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

    override suspend fun getNoticeList(teamId: String): List<NoticeDTO> {
        return try {
            db.collection(COLLECTION_NOTICE)
                .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
                .get()
                .await()
                .documents.mapNotNull { documentSnapshot ->
                    val noticeDTO = documentSnapshot.toObject(NoticeDTO::class.java)

                    val comments = documentSnapshot.reference.collection(COLLECTION_COMMENTS)
                        .get()
                        .await()
                        .toObjects(CommentDTO::class.java)

                    noticeDTO?.copy(comments = comments)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getNoticeById(noticeId: String): NoticeDTO? {
        return try {
            db.collection(COLLECTION_NOTICE)
                .document(noticeId).get().await().let { documentSnapshot ->
                    documentSnapshot.toObject(NoticeDTO::class.java)?.let { noticeDTO ->
                            documentSnapshot.reference.collection(COLLECTION_COMMENTS)
                                .get().await().toObjects(CommentDTO::class.java).let { comments ->
                                    noticeDTO.copy(comments = comments)
                                }
                        }
                }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addComment(
        noticeId: String,
        user: User,
        comment: String,
    ): Boolean = runCatching {
        val commentDTO = CommentDTO(
            userId = user.userId,
            userName = user.userName,
            upLoadTime = LocalDateTime.now().toString(),
            content = comment,
        )

        db.collection(COLLECTION_NOTICE).document(noticeId)
            .collection(COLLECTION_COMMENTS).add(commentDTO).await().apply {
                this.update(DOCUMENT_ID, this.id).await()
            }
    }.isSuccess

    override suspend fun deleteComment(
        noticeId: String,
        commentId: String,
    ): Boolean = runCatching {
        db.collection(COLLECTION_NOTICE).document(noticeId)
            .collection(COLLECTION_COMMENTS).document(commentId).delete().await()
    }.isSuccess
}