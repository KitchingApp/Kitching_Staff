package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.TodoPrepData
import com.kitching.domain.repository.PrepRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrepViewModel(private val repository: PrepRepository) : ViewModel() {
    private val _todoPrepsByDate = MutableStateFlow<AppResult<TodoPrepData>>(AppResult.Initial)
    val todoPrepsByDate get() = _todoPrepsByDate.asStateFlow()

    fun getTodoPrepsByDate(teamId: String, date: String) {
        viewModelScope.launch {
            repository.getTodoPrep(teamId, date).collectLatest {
                _todoPrepsByDate.value = it
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

    private val _updateTodoPrepResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val updateTodoPrepResult get() = _updateTodoPrepResult.asStateFlow()

    fun updateTodoPrep(todoId: String, isDone: Boolean) {
        viewModelScope.launch {
            repository.updateTodoPrep(todoId, isDone).collectLatest {
                _updateTodoPrepResult.value = it
            }
        }
    }
}