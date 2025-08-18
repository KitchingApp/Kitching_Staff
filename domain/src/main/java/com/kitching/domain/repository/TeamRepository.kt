package com.kitching.domain.repository

import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Notice
import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    suspend fun getTeamsByUserId(userId: String): Flow<AppResult<List<Team>>>

    suspend fun getTeam(teamId: String): Flow<AppResult<Team>>

    suspend fun joinTeamByInviteCode(userId: String, inviteCode: String): Flow<AppResult<Team>>

    suspend fun getAllMemberList(teamId: String): Flow<AppResult<List<Member>>>

    suspend fun getNoticeList(teamId: String): Flow<AppResult<List<Notice>>>

    suspend fun getNoticeById(noticeId: String): Flow<AppResult<Notice>>

    suspend fun addComment(noticeId: String, user: User, comment: String): Flow<AppResult<Boolean>>

    suspend fun deleteComment(noticeId: String, commentId: String): Flow<AppResult<Boolean>>
}