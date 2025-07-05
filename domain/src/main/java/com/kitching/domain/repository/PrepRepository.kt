package com.kitching.domain.repository

import com.kitching.domain.entities.Prep
import com.kitching.domain.entities.PrepCategory
import com.kitching.domain.entities.TodoPrepByCategory
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface PrepRepository {
    fun getTodoPrep(teamId: String, date: String): Flow<AppResult<List<TodoPrepByCategory>>>

    fun createTodoPrep(teamId: String, date: String, categoryId: String, prepId: String): Flow<AppResult<Boolean>>

    fun updateTodoPrep(todoId: String, isNone: Boolean): Flow<AppResult<Boolean>>

    fun getPrepCategory(teamId: String): Flow<AppResult<List<PrepCategory>>>

    fun getPreps(teamId: String): Flow<AppResult<List<Prep>>>
}