package com.kitching.domain.repository

import com.kitching.domain.entities.TodoPrepData
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface PrepRepository {
    fun getTodoPrep(teamId: String, date: String): Flow<AppResult<TodoPrepData>>

    fun createTodoPrep(teamId: String, date: String, categoryId: String, prepId: String): Flow<AppResult<Boolean>>

    fun updateTodoPrep(todoId: String, isDone: Boolean): Flow<AppResult<Boolean>>

    fun deleteTodoPrep(todoId: String): Flow<AppResult<Boolean>>
}