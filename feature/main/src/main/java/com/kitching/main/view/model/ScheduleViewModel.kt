package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.core.exception.getDisplayMessage
import com.kitching.domain.entities.Schedule
import com.kitching.domain.entities.ScheduleTime
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {

    private val _mySchedules = MutableStateFlow(UiState<List<Schedule>>())
    val mySchedules get() = _mySchedules.asStateFlow()

    fun fetchSchedules(userId: String, teamId: String) {
        viewModelScope.launch {
            scheduleRepository.getMySchedules(userId, teamId).collectLatest { result ->
                    when (result) {
                        is AppResult.Loading -> {
                            _mySchedules.value = _mySchedules.value.toLoading()
                        }

                        is AppResult.Success -> {
                            _mySchedules.value = _mySchedules.value.toSuccess(result.data)
                        }

                        is AppResult.Failure -> {
                            _mySchedules.value = _mySchedules.value.toError(result.getDisplayMessage())
                        }
                    }
                }
        }
    }

    private val _scheduleByDate = MutableStateFlow(UiState<List<Schedule>>())
    val scheduleByDate get() = _scheduleByDate.asStateFlow()

    fun fetchScheduleByDate(teamId: String, date: String) {
        viewModelScope.launch {
            scheduleRepository.getScheduleByDate(teamId, date).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _scheduleByDate.value = _scheduleByDate.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _scheduleByDate.value = _scheduleByDate.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _scheduleByDate.value = _scheduleByDate.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private val _scheduleTimes = MutableStateFlow(UiState<List<ScheduleTime>>())
    val scheduleTimes get() = _scheduleTimes.asStateFlow()

    fun getScheduleTimes(teamId: String) {
        viewModelScope.launch {
            scheduleRepository.getScheduleTimes(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _scheduleTimes.value = _scheduleTimes.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _scheduleTimes.value = _scheduleTimes.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _scheduleTimes.value = _scheduleTimes.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private val _scheduleResult = MutableStateFlow(UiState<Boolean>())
    val scheduleResult get() = _scheduleResult.asStateFlow()

    fun createSchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean = false
    ) {
        viewModelScope.launch {
            scheduleRepository.createApplySchedule(teamId, dateString, userId, scheduleTimeId, fix)
                .collectLatest { result ->
                    when (result) {
                        is AppResult.Loading -> {
                            _scheduleResult.value = _scheduleResult.value.toLoading()
                        }

                        is AppResult.Success -> {
                            _scheduleResult.value = _scheduleResult.value.toSuccess(result.data)
                            fetchScheduleByDate(teamId, dateString)
                        }

                        is AppResult.Failure -> {
                            _scheduleResult.value = _scheduleResult.value.toError(result.getDisplayMessage())
                        }
                    }
                }
        }
    }
}