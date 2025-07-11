package com.kitching.domain.repository

import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Notice
import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeamsByUserId(userId: String): Flow<AppResult<List<Team>>>

    fun getTeam(teamId: String): Flow<AppResult<Team>>

    fun joinTeamByInviteCode(userId: String, inviteCode: String): Flow<AppResult<Team>>

    fun getAllMemberList(teamId: String): Flow<AppResult<List<Member>>>

    fun getNoticeList(teamId: String): Flow<AppResult<List<Notice>>>

    fun getNoticeById(noticeId: String): Flow<AppResult<Notice>>

    fun addComment(noticeId: String, user: User, comment: String): Flow<AppResult<Boolean>>
}