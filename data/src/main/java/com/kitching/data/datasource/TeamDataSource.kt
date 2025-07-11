package com.kitching.data.datasource

import com.kitching.data.dto.NoticeDTO
import com.kitching.data.dto.TeamDTO
import com.kitching.domain.entities.User

interface TeamDataSource {
    suspend fun getTeam(teamId: String): TeamDTO?

    suspend fun getTeamList(teamId: String): List<TeamDTO>

    suspend fun getTeamByInviteCode(inviteCode: String): TeamDTO?

    suspend fun getNoticeList(teamId: String): List<NoticeDTO>

    suspend fun getNoticeById(noticeId: String): NoticeDTO?

    suspend fun addComment(noticeId: String, user: User, comment: String): Boolean

    suspend fun deleteComment(noticeId: String, commentId: String): Boolean
}