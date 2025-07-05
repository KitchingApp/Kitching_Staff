package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Prep
import com.kitching.domain.entities.PrepCategory
import com.kitching.domain.entities.TodoPrepByCategory
import com.kitching.domain.repository.PrepRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrepViewModel(private val repository: PrepRepository) : ViewModel() {
    private val _todoPrepsByDate = MutableStateFlow<AppResult<List<TodoPrepByCategory>>>(AppResult.Initial)
    val todoPrepsByDate get() = _todoPrepsByDate.asStateFlow()

    fun getTodoPrepsByDate(teamId: String, date: String) {
        viewModelScope.launch {
            repository.getTodoPrep(teamId, date).collectLatest {
                _todoPrepsByDate.value = it
            }
        }
    }

    private val _prepCategories = MutableStateFlow<AppResult<List<PrepCategory>>>(AppResult.Initial)
    val prepCategories get() = _prepCategories.asStateFlow()

    fun getPrepCategories(teamId: String) {
        viewModelScope.launch {
            repository.getPrepCategory(teamId).collectLatest {
                _prepCategories.value = it
            }
        }
    }

    private val _preps = MutableStateFlow<AppResult<List<Prep>>>(AppResult.Initial)
    val preps get() = _preps.asStateFlow()

    fun getPreps(teamId: String) {
        viewModelScope.launch {
            repository.getPreps(teamId).collectLatest {
                _preps.value = it
            }
        }
    }

    private val _createTodoPrepResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val createTodoPrepResult get() = _createTodoPrepResult.asStateFlow()

    fun createTodoPrep(teamId: String, date: String, categoryId: String, prepId: String) {
        viewModelScope.launch {
            repository.createTodoPrep(teamId, date, categoryId, prepId).collectLatest {
                _createTodoPrepResult.value = it

                if (it is AppResult.Success) {
                    getTodoPrepsByDate(teamId, date)
                }
            }
        }
    }
}