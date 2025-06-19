package com.kitching.data.repository

import com.kitching.data.datasource.TeamDataSource
import com.kitching.data.datasource.TeamDataSourceImpl
import com.kitching.data.datasource.UserTeamDataSource
import com.kitching.data.datasource.UserTeamDataSourceImpl
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID

class TeamRepositoryImpl(
    private val teamDataSource: TeamDataSource = TeamDataSourceImpl(),
    private val userTeamDataSource: UserTeamDataSource = UserTeamDataSourceImpl(),
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
                AppResult.Success(team.toDomain())
            )
        } else throw Throwable("team is not exists")
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun joinTeamByInviteCode(
        userId: String,
        inviteCode: String,
    ) = flow {
        emit(AppResult.Loading)
        val team = teamDataSource.getTeamByInviteCode(inviteCode)

        if (team != null) {
            val joinSuccess = userTeamDataSource.createUserTeam(
                userId = userId,
                teamId = team.id
            )

            if (joinSuccess) {
                emit(AppResult.Success(team.toDomain()))
            } else {
                emit(AppResult.Failure(Exception("팀 참여 실패")))
            }
        } else {
            emit(AppResult.Failure(Exception("해당 초대코드의 팀이 없습니다. 다시 입력해주세요.")))
        }
    }.catch {
        emit(AppResult.Failure(it))
    }
}