package com.kitching.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Schedule
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {

    private val _schedules = MutableStateFlow<AppResult<List<Schedule>>>(AppResult.Initial)
    val schedules get() = _schedules.asStateFlow()

    fun fetchSchedules(userId: String, teamId: String) {
        viewModelScope.launch {
            scheduleRepository.getSchedules(userId, teamId)
                .collectLatest {
                    _schedules.value = it
                }
        }
    }
}