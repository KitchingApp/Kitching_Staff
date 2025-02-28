package com.kitching.data.datasource

import com.kitching.data.dto.UserTeamDTO

interface UserTeamDataSource {
    suspend fun getAllMembers(teamId: String): List<UserTeamDTO>

    suspend fun getMember(teamId: String, userId: String): UserTeamDTO?

    suspend fun getUserTeams(userId: String): List<UserTeamDTO>

    suspend fun createUserTeams(userId: String, teamId: String, staffLevelId: String, manager: Boolean = true): Boolean

    suspend fun updateMember(userTeamId: String, staffLevelId: String, manager: Boolean): Boolean
}