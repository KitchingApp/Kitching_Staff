package com.kitching.main.view.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrawerViewModel(private val teamRepository: TeamRepository) : ViewModel() {
    private val _teamListResult = MutableStateFlow<AppResult<List<Team>>>(AppResult.Initial)
    val teamListResult get() = _teamListResult.asStateFlow()

    fun getTeamListByUserId(userId: String) {
        viewModelScope.launch {
            teamRepository.getTeamsByUserId(userId).collectLatest {
                _teamListResult.value = it
            }
        }
    }

    private val _teamChangeResult = MutableStateFlow<AppResult<Team>>(AppResult.Initial)
    val teamChangeResult get() = _teamChangeResult.asStateFlow()

    fun changeTeam(context: Context, teamId: String) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveTeamId(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Success<*> -> {
                        loadTeam(teamId)
                    }
                    is AppResult.Failure -> {
                        _teamChangeResult.value = AppResult.Failure(result.exception)
                    }
                    AppResult.Loading -> AppResult.Loading
                    AppResult.Initial -> AppResult.Initial
                }
            }
        }
    }

    private fun loadTeam(teamId: String) {
        viewModelScope.launch {
            teamRepository.getTeam(teamId).collectLatest {
                _teamChangeResult.value = it
            }
        }
    }
}