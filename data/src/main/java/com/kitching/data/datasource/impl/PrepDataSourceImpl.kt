package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.datasource.PrepDataSource
import com.kitching.data.dto.PrepCategoryDTO
import com.kitching.data.dto.PrepDTO
import com.kitching.data.dto.TodoPrepDTO
import com.kitching.data.firebase.COLLECTION_PREP
import com.kitching.data.firebase.COLLECTION_PREP_CATEGORY
import com.kitching.data.firebase.COLLECTION_TODO_PREP
import com.kitching.data.firebase.DOCUMENT_DATE
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import com.kitching.data.firebase.DOCUMENT_TODO_PREP_DONE
import kotlinx.coroutines.tasks.await

class PrepDataSourceImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : PrepDataSource {
    override suspend fun getTodoPrep(
        teamId: String,
        date: String,
    ): List<TodoPrepDTO> {
        return db.collection(COLLECTION_TODO_PREP)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_DATE, date)
            .get()
            .await()
            .toObjects(TodoPrepDTO::class.java)
    }

    override suspend fun createTodoPrep(todoPrepDTO: TodoPrepDTO): Boolean = runCatching {
        db.collection(COLLECTION_TODO_PREP).add(todoPrepDTO).await().apply {
            update(DOCUMENT_ID, id).await()
        }
    }.isSuccess

    override suspend fun updateTodoPrep(
        todoId: String,
        isDone: Boolean,
    ): Boolean = runCatching {
        db.collection(COLLECTION_TODO_PREP).document(todoId).update(DOCUMENT_TODO_PREP_DONE, isDone).await()
    }.isSuccess

    override suspend fun deleteTodoPrep(todoId: String): Boolean = runCatching {
        db.collection(COLLECTION_TODO_PREP).document(todoId).delete().await()
    }.isSuccess

    override suspend fun getPrepCategory(teamId: String): List<PrepCategoryDTO> {
        return db.collection(COLLECTION_PREP_CATEGORY)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .toObjects(PrepCategoryDTO::class.java)
    }

    override suspend fun getPreps(teamId: String): List<PrepDTO> {
        return db.collection(COLLECTION_PREP)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .toObjects(PrepDTO::class.java)
    }
}