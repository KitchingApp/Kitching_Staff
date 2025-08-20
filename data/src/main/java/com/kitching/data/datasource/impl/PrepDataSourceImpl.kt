package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.core.exception.ExceptionHandler
import com.kitching.core.exception.KitchingRuntimeException
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrepDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : PrepDataSource {
    override suspend fun getTodoPrep(
        teamId: String,
        date: String,
    ): List<TodoPrepDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_TODO_PREP)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .whereEqualTo(DOCUMENT_DATE, date)
            .get()
            .await()
            .toObjects(TodoPrepDTO::class.java)
    }

    override suspend fun createTodoPrep(todoPrepDTO: TodoPrepDTO): Boolean = ExceptionHandler.safeCall {
        val documentTodoPrep = db.collection(COLLECTION_TODO_PREP).add(todoPrepDTO).await().apply {
            update(DOCUMENT_ID, id).await()
        }

        val collectionTodoPrep = documentTodoPrep.get().await()

        if (!collectionTodoPrep.exists()) {
            throw KitchingRuntimeException.TodoPrepCreateFailedException()
        }

        true
    }

    override suspend fun updateTodoPrep(
        todoId: String,
        isDone: Boolean,
    ): Boolean = ExceptionHandler.safeCall {
        db.collection(COLLECTION_TODO_PREP).document(todoId).update(DOCUMENT_TODO_PREP_DONE, isDone).await()

        val updatedTodoPrep = db.collection(COLLECTION_TODO_PREP).document(todoId).get().await().getBoolean(DOCUMENT_TODO_PREP_DONE)

        if (updatedTodoPrep != isDone) {
            throw KitchingRuntimeException.TodoPrepUpdateFailedException()
        }

        true
    }

    override suspend fun deleteTodoPrep(todoId: String): Boolean = ExceptionHandler.safeCall {
        db.collection(COLLECTION_TODO_PREP).document(todoId).delete().await()

        val deletedTodoPrep = db.collection(COLLECTION_TODO_PREP).document(todoId).get().await()

        if (deletedTodoPrep.exists()) {
            throw KitchingRuntimeException.TodoPrepDeleteFailedException()
        }

        true
    }

    override suspend fun getPrepCategory(teamId: String): List<PrepCategoryDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_PREP_CATEGORY)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .toObjects(PrepCategoryDTO::class.java)
    }

    override suspend fun getPreps(teamId: String): List<PrepDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_PREP)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .toObjects(PrepDTO::class.java)
    }
}