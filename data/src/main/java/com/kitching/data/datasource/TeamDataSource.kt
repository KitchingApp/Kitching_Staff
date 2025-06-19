package com.kitching.data.datasource

import com.kitching.data.dto.TeamDTO

interface TeamDataSource {
    suspend fun getTeam(teamId: String): TeamDTO?

    suspend fun getTeamList(teamId: String): List<TeamDTO>

    suspend fun getTeamByInviteCode(inviteCode: String): TeamDTO?
}