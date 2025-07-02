package com.kitching.domain.entities

data class TodoPrep(
    val id: String = "",
    val date: String = "",
    val categoryId: String = "",
    val prepId: String = "",
    val isDone: Boolean = false,
)

data class TodoPrepWithDetails(
    val todoPrep: TodoPrep,
    val categoryName: String,
    val categoryColor: String,
    val prepName: String,
)

data class TodoPrepByCategory(
    val category: PrepCategory,
    val todos: List<TodoPrepWithDetails>
)