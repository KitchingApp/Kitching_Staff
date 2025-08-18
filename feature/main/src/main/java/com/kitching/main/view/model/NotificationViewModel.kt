package com.kitching.main.view.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.core.exception.getDisplayMessage
import com.kitching.data.repository.NotificationRepositoryImpl
import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification
import com.kitching.domain.repository.NotificationRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationViewModel(context: Context) : ViewModel() {

    private val repository: NotificationRepository by lazy {
        NotificationRepositoryImpl(context)
    }

    private val _scheduleNotificationList = MutableStateFlow(UiState<List<ScheduleNotification>>())
    val scheduleNotificationList get() = _scheduleNotificationList.asStateFlow()

    fun fetchScheduleNotificationList() {
        viewModelScope.launch {
            repository.getScheduleNotifications().collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _scheduleNotificationList.value = _scheduleNotificationList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _scheduleNotificationList.value = _scheduleNotificationList.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _scheduleNotificationList.value = _scheduleNotificationList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private val _noticeNotificationList = MutableStateFlow(UiState<List<NoticeNotification>>())
    val noticeNotificationList get() = _noticeNotificationList.asStateFlow()

    fun fetchNoticeNotificationList() {
        viewModelScope.launch {
            repository.getNoticeNotifications().collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _noticeNotificationList.value = _noticeNotificationList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _noticeNotificationList.value = _noticeNotificationList.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _noticeNotificationList.value = _noticeNotificationList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    fun deleteScheduleNotification(id: Long) {
        viewModelScope.launch {
            repository.deleteScheduleNotification(id).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _scheduleNotificationList.value = _scheduleNotificationList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        fetchScheduleNotificationList()
                    }

                    is AppResult.Failure -> {
                        _scheduleNotificationList.value = _scheduleNotificationList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    fun deleteNoticeNotification(id: Long) {
        viewModelScope.launch {
            repository.deleteNoticeNotification(id).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _noticeNotificationList.value = _noticeNotificationList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        fetchNoticeNotificationList()
                    }

                    is AppResult.Failure -> {
                        _noticeNotificationList.value = _noticeNotificationList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    fun deleteAllScheduleNotification() {
        viewModelScope.launch {
            repository.deleteAllScheduleNotification().collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _scheduleNotificationList.value = _scheduleNotificationList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        fetchScheduleNotificationList()
                    }

                    is AppResult.Failure -> {
                        _scheduleNotificationList.value = _scheduleNotificationList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    fun deleteAllNoticeNotification() {
        viewModelScope.launch {
            repository.deleteAllNoticeNotification().collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _noticeNotificationList.value = _noticeNotificationList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        fetchNoticeNotificationList()
                    }

                    is AppResult.Failure -> {
                        _noticeNotificationList.value = _noticeNotificationList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }
}