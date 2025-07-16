package com.kitching.data.repository

import com.kitching.data.datasource.PrepDataSource
import com.kitching.data.datasource.impl.PrepDataSourceImpl
import com.kitching.data.dto.TodoPrepDTO
import com.kitching.domain.entities.TodoPrepData
import com.kitching.domain.entities.TodoPrepWithDetails
import com.kitching.domain.repository.PrepRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PrepRepositoryImpl(private val dataSource: PrepDataSource = PrepDataSourceImpl()) :
    PrepRepository {
    override fun getTodoPrep(
        teamId: String,
        date: String,
    ): Flow<AppResult<TodoPrepData>> = flow {
        emit(AppResult.Loading)

        val todoPreps = dataSource.getTodoPrep(teamId, date)
        val categories = dataSource.getPrepCategory(teamId)
        val preps = dataSource.getPreps(teamId)

        val todoPrepWithDetails = todoPreps.map { todoPrepsDTO ->
            val prep = preps.find { it.id == todoPrepsDTO.prepId }
            val category = categories.find { it.id == todoPrepsDTO.categoryId }

            TodoPrepWithDetails(
                todoPrep = todoPrepsDTO.toDomain(),
                categoryName = category?.name ?: "",
                categoryColor = category?.color ?: "",
                prepName = prep?.name ?: "",
            )
        }

        val todoPrepData = TodoPrepData(
            categories = categories.map { it.toDomain() },
            preps = preps.map { it.toDomain() },
            todos = todoPrepWithDetails
        )

        emit(AppResult.Success(todoPrepData))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createTodoPrep(
        teamId: String,
        date: String,
        categoryId: String,
        prepId: String,
    ): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        val todoPrep = TodoPrepDTO(
            teamId = teamId,
            date = date,
            categoryId = categoryId,
            prepId = prepId,
            done = false
        )

        val result = dataSource.createTodoPrep(todoPrep)

        emit(AppResult.Success(result))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun updateTodoPrep(
        todoId: String,
        isDone: Boolean,
    ): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        val result = dataSource.updateTodoPrep(todoId, isDone)

        emit(AppResult.Success(result))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun deleteTodoPrep(todoId: String): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        val result = dataSource.deleteTodoPrep(todoId)

        emit(AppResult.Success(result))
    }.catch {
        emit(AppResult.Failure(it))

    }
}