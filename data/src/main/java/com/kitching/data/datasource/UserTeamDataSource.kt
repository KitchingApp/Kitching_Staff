package com.kitching.data.datasource

import com.kitching.data.dto.UserTeamDTO

interface UserTeamDataSource {
    suspend fun getAllMembers(teamId: String): List<UserTeamDTO>

    suspend fun getMember(teamId: String, userId: String): UserTeamDTO?

    suspend fun getUserTeams(userId: String): List<UserTeamDTO>

    suspend fun createUserTeam(userId: String, teamId: String): Boolean
}