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
import com.kitching.domain.exception.ExceptionHandler
import com.kitching.domain.exception.KitchingRuntimeException
import com.kitching.domain.entities.User
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

class TeamDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    TeamDataSource {
    override suspend fun getTeam(teamId: String) = ExceptionHandler.safeCall {
        db.collection(COLLECTION_TEAM).document(teamId).get().await()
            .toObject(TeamDTO::class.java) ?: throw KitchingRuntimeException.TeamNotFoundException()
    }

    override suspend fun getTeamList(teamId: String): List<TeamDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_TEAM).whereEqualTo(DOCUMENT_ID, teamId).get().await()
            .toObjects(TeamDTO::class.java)
    }

    override suspend fun getTeamByInviteCode(inviteCode: String): TeamDTO = ExceptionHandler.safeCall {
        val teamDto = db.collection(COLLECTION_TEAM).whereEqualTo(DOCUMENT_INVITE_CODE, inviteCode).get().await()
            .documents.firstOrNull()?.toObject(TeamDTO::class.java)

        teamDto ?: throw KitchingRuntimeException.TeamNotFoundException()
    }

    override suspend fun getNoticeList(teamId: String): List<NoticeDTO> = ExceptionHandler.safeCall {
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
    }

    override suspend fun getNoticeById(noticeId: String): NoticeDTO? = ExceptionHandler.safeCall {
        db.collection(COLLECTION_NOTICE)
            .document(noticeId).get().await().let { documentSnapshot ->
                documentSnapshot.toObject(NoticeDTO::class.java)?.let { noticeDTO ->
                    documentSnapshot.reference.collection(COLLECTION_COMMENTS)
                        .get().await().toObjects(CommentDTO::class.java).let { comments ->
                            noticeDTO.copy(comments = comments)
                        }
                }
            }
    }

    override suspend fun addComment(
        noticeId: String,
        user: User,
        comment: String,
    ): Boolean = ExceptionHandler.safeCall {
        val commentDTO = CommentDTO(
            userId = user.userId,
            userName = user.userName,
            upLoadTime = LocalDateTime.now().toString(),
            content = comment,
        )

        val newComment = db.collection(COLLECTION_NOTICE).document(noticeId)
            .collection(COLLECTION_COMMENTS).add(commentDTO).await().apply {
                update(DOCUMENT_ID, id).await()
            }

        val createdComment = newComment.get().await()

        if (createdComment.exists()) {
            true
        } else {
            throw KitchingRuntimeException.CommentAddFailedException()
        }
    }

    override suspend fun deleteComment(
        noticeId: String,
        commentId: String,
    ): Boolean = ExceptionHandler.safeCall {
        db.collection(COLLECTION_NOTICE).document(noticeId)
            .collection(COLLECTION_COMMENTS).document(commentId).delete().await()

        val deletedComment = db.collection(COLLECTION_NOTICE).document(noticeId)
            .collection(COLLECTION_COMMENTS).document(commentId).get().await()

        if (!deletedComment.exists()) {
            true
        } else {
            throw KitchingRuntimeException.CommentDeleteFailedException()
        }
    }
}