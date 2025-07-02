package com.kitching.data.dto

import com.kitching.domain.entities.TodoPrep

data class TodoPrepDTO(
    val id: String = "",
    val teamId: String = "",
    val date: String = "",
    val categoryId: String = "",
    val prepId: String = "",
    val isDone: Boolean = false,
) {
    fun toDomain(): TodoPrep {
        return TodoPrep(
            id = id,
            date = date,
            categoryId = categoryId,
            prepId = prepId,
            isDone = isDone
        )
    }
}