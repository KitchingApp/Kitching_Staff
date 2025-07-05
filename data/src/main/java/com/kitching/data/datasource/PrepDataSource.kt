package com.kitching.data.datasource

import com.kitching.data.dto.PrepCategoryDTO
import com.kitching.data.dto.PrepDTO
import com.kitching.data.dto.TodoPrepDTO

interface PrepDataSource {
    suspend fun getTodoPrep(teamId: String, date: String): List<TodoPrepDTO>

    suspend fun createTodoPrep(todoPrepDTO: TodoPrepDTO): Boolean

    suspend fun updateTodoPrep(todoId: String, isNone: Boolean): Boolean

    suspend fun getPrepCategory(teamId: String): List<PrepCategoryDTO>

    suspend fun getPreps(teamId: String): List<PrepDTO>
}