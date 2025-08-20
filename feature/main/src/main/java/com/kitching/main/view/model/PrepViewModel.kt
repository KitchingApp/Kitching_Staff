package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.core.exception.getDisplayMessage
import com.kitching.domain.entities.TodoPrepData
import com.kitching.domain.repository.PrepRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrepViewModel @Inject constructor(
    private val repository: PrepRepository
) : ViewModel() {
    private val _todoPrepsByDate = MutableStateFlow(UiState<TodoPrepData>())
    val todoPrepsByDate get() = _todoPrepsByDate.asStateFlow()

    fun getTodoPrepsByDate(teamId: String, date: String) {
        viewModelScope.launch {
            repository.getTodoPrep(teamId, date).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _todoPrepsByDate.value = _todoPrepsByDate.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _todoPrepsByDate.value = _todoPrepsByDate.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _todoPrepsByDate.value = _todoPrepsByDate.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private val _actionTodoPrepResult = MutableStateFlow(UiState<Boolean>())
    val actionTodoPrepResult get() = _actionTodoPrepResult.asStateFlow()

    fun createTodoPrep(teamId: String, date: String, categoryId: String, prepId: String) {
        viewModelScope.launch {
            repository.createTodoPrep(teamId, date, categoryId, prepId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    fun updateTodoPrep(todoId: String, isDone: Boolean) {
        viewModelScope.launch {
            repository.updateTodoPrep(todoId, isDone).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    fun deleteTodoPrep(todoId: String) {
        viewModelScope.launch {
            repository.deleteTodoPrep(todoId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _actionTodoPrepResult.value = _actionTodoPrepResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }
}