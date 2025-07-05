package com.kitching.data.repository

import com.kitching.data.datasource.PrepDataSource
import com.kitching.data.datasource.impl.PrepDataSourceImpl
import com.kitching.data.dto.TodoPrepDTO
import com.kitching.domain.entities.Prep
import com.kitching.domain.entities.PrepCategory
import com.kitching.domain.entities.TodoPrepByCategory
import com.kitching.domain.entities.TodoPrepWithDetails
import com.kitching.domain.repository.PrepRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PrepRepositoryImpl(private val dataSource: PrepDataSource = PrepDataSourceImpl()) :
    PrepRepository {
    override fun getTodoPrep(
        teamId: String,
        date: String,
    ): Flow<AppResult<List<TodoPrepByCategory>>> = flow {
        emit(AppResult.Loading)

        try {
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

            val groupedByCategory = todoPrepWithDetails
                .groupBy { it.todoPrep.categoryId }
                .mapNotNull { (categoryId, todos) ->
                    val category = categories.find { it.id == categoryId }

                    if (category != null) {
                        TodoPrepByCategory(
                            category = category.toDomain(),
                            todos = todos
                        )
                    } else null
                }
                .sortedBy { it.category.categoryName }

            emit(AppResult.Success(groupedByCategory))

        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }

    override fun createTodoPrep(
        teamId: String,
        date: String,
        categoryId: String,
        prepId: String,
    ): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        try {
            val todoPrep = TodoPrepDTO(
                teamId = teamId,
                date = date,
                categoryId = categoryId,
                prepId = prepId,
                done = false
            )

            val result = dataSource.createTodoPrep(todoPrep)

            emit(AppResult.Success(result))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }

    override fun updateTodoPrep(
        todoId: String,
        isNone: Boolean,
    ): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        try {
            val result = dataSource.updateTodoPrep(todoId, isNone)
            emit(AppResult.Success(result))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }

    override fun getPrepCategory(teamId: String): Flow<AppResult<List<PrepCategory>>> = flow {
        emit(AppResult.Loading)

        try {
            val categories = dataSource.getPrepCategory(teamId)
            emit(AppResult.Success(categories.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }

    override fun getPreps(teamId: String): Flow<AppResult<List<Prep>>> = flow {
        emit(AppResult.Loading)

        try {
            val preps = dataSource.getPreps(teamId)
            emit(AppResult.Success(preps.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }
}