package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Member
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OtherViewModel(val repository: TeamRepository) : ViewModel() {
    private val _memberListResult = MutableStateFlow<AppResult<List<Member>>>(AppResult.Initial)
    val memberListResult get() = _memberListResult.asStateFlow()

    fun getAllMemberList(teamId: String) {
        viewModelScope.launch {
            repository.getAllMemberList(teamId).collectLatest {
                _memberListResult.value = it
            }
        }
    }
}