package com.kitching.data.repository

import com.kitching.data.datasource.TeamDataSource
import com.kitching.data.datasource.TeamDataSourceImpl
import com.kitching.data.datasource.UserTeamDataSource
import com.kitching.data.datasource.UserTeamDataSourceImpl
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID

class TeamRepositoryImpl(
    private val teamDataSource: TeamDataSource = TeamDataSourceImpl(),
    private val userTeamDataSource: UserTeamDataSource = UserTeamDataSourceImpl()
) : TeamRepository {
    override fun getTeamsByUserId(userId: String) = flow {
        emit(AppResult.Loading)

        val userTeams = userTeamDataSource.getUserTeams(userId)

        val teamList = userTeams.flatMap { userTeamDTO ->
            teamDataSource.getTeamList(userTeamDTO.teamId).map { dto ->
                Team(
                    teamId = dto.id,
                    teamName = dto.teamName,
                    teamAmount = dto.teamAmount
                )
            }
        }
        emit(AppResult.Success(teamList))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun getTeam(teamId: String) = flow {
        emit(AppResult.Loading)
        val team = teamDataSource.getTeam(teamId)
        if (team != null) {
            emit(
                AppResult.Success(
                    Team(
                        teamId = team.id,
                        teamName = team.teamName,
                        teamAmount = team.teamAmount,
                        inviteCode = team.inviteCode
                    )
                )
            )
        } else throw Throwable("team is not exists")
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createTeam(
        ownerId: String, teamName: String, teamAmount: Int
    ) = flow {
        emit(AppResult.Loading)
        val inviteCode = UUID.randomUUID().toString().replace("-", "")
            emit(
                AppResult.Success(
                    userTeamDataSource.createUserTeams(
                        userId = ownerId,
                        teamId = teamDataSource.createTeam(
                            inviteCode,
                            ownerId,
                            teamName,
                            teamAmount
                        ),
                        staffLevelId = ""
                    )
                )
            )
    }.catch {
        emit(AppResult.Failure(it))
    }
}