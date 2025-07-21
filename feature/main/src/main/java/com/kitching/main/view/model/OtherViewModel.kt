package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Notice
import com.kitching.domain.entities.User
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OtherViewModel(val repository: TeamRepository) : ViewModel() {
    private val _memberListResult = MutableStateFlow(UiState<List<Member>>())
    val memberListResult get() = _memberListResult.asStateFlow()

    fun getAllMemberList(teamId: String) {
        viewModelScope.launch {
            repository.getAllMemberList(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _memberListResult.value = _memberListResult.value
                    }

                    is AppResult.Loading -> {
                        _memberListResult.value = _memberListResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _memberListResult.value = _memberListResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _memberListResult.value = _memberListResult.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private val _noticeListResult = MutableStateFlow(UiState<List<Notice>>())
    val noticeListResult get() = _noticeListResult.asStateFlow()

    fun getNoticeList(teamId: String) {
        viewModelScope.launch {
            repository.getNoticeList(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _noticeListResult.value = _noticeListResult.value
                    }

                    is AppResult.Loading -> {
                        _noticeListResult.value = _noticeListResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _noticeListResult.value = _noticeListResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _noticeListResult.value = _noticeListResult.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private val _noticeByIdResult = MutableStateFlow(UiState<Notice>())
    val noticeByIdResult get() = _noticeByIdResult.asStateFlow()

    fun getNoticeById(noticeId: String) {
        viewModelScope.launch {
            repository.getNoticeById(noticeId).collectLatest { result ->
                when(result) {
                    is AppResult.Initial -> {
                        _noticeByIdResult.value = _noticeByIdResult.value
                    }

                    is AppResult.Loading -> {
                        _noticeByIdResult.value = _noticeByIdResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _noticeByIdResult.value = _noticeByIdResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _noticeByIdResult.value = _noticeByIdResult.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private val _commentAction = MutableStateFlow(UiState<Boolean>())
    val commentAction get() = _commentAction.asStateFlow()

    fun addComment(noticeId: String, user: User, comment: String) {
        viewModelScope.launch {
            repository.addComment(noticeId, user, comment).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _commentAction.value = _commentAction.value
                    }

                    is AppResult.Loading -> {
                        _commentAction.value = _commentAction.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _commentAction.value = _commentAction.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _commentAction.value = _commentAction.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    fun deleteComment(noticeId: String, commentId: String) {
        viewModelScope.launch {
            repository.deleteComment(noticeId, commentId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _commentAction.value = _commentAction.value
                    }

                    is AppResult.Loading -> {
                        _commentAction.value = _commentAction.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _commentAction.value = _commentAction.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _commentAction.value = _commentAction.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }
}