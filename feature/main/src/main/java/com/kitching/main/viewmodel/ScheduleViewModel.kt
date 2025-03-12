package com.kitching.main.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.Schedule
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class ScheduleViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {

    private val _teamId = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val teamId = _teamId.asStateFlow()

    fun getTeamIdFromDataStore(context: Context) {
        viewModelScope.launch {
            PreferencesDataSource(context).getTeamId().collectLatest {
                _teamId.value = it
            }
        }
    }

    private val _mySchedules = MutableStateFlow<AppResult<List<Schedule>>>(AppResult.Initial)
    val mySchedules get() = _mySchedules.asStateFlow()

    fun fetchSchedules(userId: String, teamId: String) {
        viewModelScope.launch {
            scheduleRepository.getMySchedules(userId, teamId)
                .collectLatest {
                    _mySchedules.value = it
                }
        }
    }

    private val _scheduleByDate = MutableStateFlow<AppResult<List<Schedule>>>(AppResult.Initial)
    val scheduleByDate get() = _scheduleByDate.asStateFlow()

    fun fetchScheduleByDate(teamId: String, date: LocalDate) {
        viewModelScope.launch {
            scheduleRepository.getScheduleByDate(teamId, date)
                .collectLatest {
                    _scheduleByDate.value = it
                }
        }
    }
}