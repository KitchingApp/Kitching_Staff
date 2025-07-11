package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Notice
import com.kitching.domain.entities.User
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

    private val _noticeListResult = MutableStateFlow<AppResult<List<Notice>>>(AppResult.Initial)
    val noticeListResult get() = _noticeListResult.asStateFlow()

    fun getNoticeList(teamId: String) {
        viewModelScope.launch {
            repository.getNoticeList(teamId).collectLatest {
                _noticeListResult.value = it
            }
        }
    }

    private val _noticeByIdResult = MutableStateFlow<AppResult<Notice>>(AppResult.Initial)
    val noticeByIdResult get() = _noticeByIdResult.asStateFlow()

    fun getNoticeById(noticeId: String) {
        viewModelScope.launch {
            repository.getNoticeById(noticeId).collectLatest {
                _noticeByIdResult.value = it
            }
        }
    }

    private val _addCommentResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val addCommentResult get() = _addCommentResult.asStateFlow()

    fun addComment(noticeId: String, user: User, comment: String) {
        viewModelScope.launch {
            repository.addComment(noticeId, user, comment).collectLatest {
                _addCommentResult.value = it
            }
        }
    }

    fun resetAddCommentResult() {
        _addCommentResult.value = AppResult.Initial
    }
}