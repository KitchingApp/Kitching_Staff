package com.kitching.main.view.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import com.kitching.domain.util.getDisplayMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrawerViewModel(private val teamRepository: TeamRepository) : ViewModel() {
    private val _teamListResult = MutableStateFlow(UiState<List<Team>>())
    val teamListResult get() = _teamListResult.asStateFlow()

    fun getTeamListByUserId(userId: String) {
        viewModelScope.launch {
            teamRepository.getTeamsByUserId(userId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _teamListResult.value = _teamListResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _teamListResult.value = _teamListResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _teamListResult.value = _teamListResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private val _teamChangeResult = MutableStateFlow(UiState<Team>())
    val teamChangeResult get() = _teamChangeResult.asStateFlow()

    fun changeTeam(context: Context, teamId: String) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveTeamId(teamId)

            loadTeam(teamId)
        }
    }

    private fun loadTeam(teamId: String) {
        viewModelScope.launch {
            teamRepository.getTeam(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _teamChangeResult.value = _teamChangeResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _teamChangeResult.value = _teamChangeResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _teamChangeResult.value = _teamChangeResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }
}