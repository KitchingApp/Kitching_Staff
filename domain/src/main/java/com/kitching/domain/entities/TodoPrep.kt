package com.kitching.domain.entities

data class TodoPrep(
    val id: String,
    val date: String,
    val categoryId: String,
    val prepId: String,
    val done: Boolean,
)

data class TodoPrepWithDetails(
    val todoPrep: TodoPrep,
    val categoryName: String,
    val categoryColor: String,
    val prepName: String,
)

data class TodoPrepData(
    val categories: List<PrepCategory>,
    val preps: List<Prep>,
    val todos: List<TodoPrepWithDetails>
)