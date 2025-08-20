package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.core.exception.getDisplayMessage
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    private val preferencesDataSource: PreferencesDataSource
) : ViewModel() {
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

    fun changeTeam(teamId: String) {
        viewModelScope.launch {
            preferencesDataSource.saveTeamId(teamId)

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