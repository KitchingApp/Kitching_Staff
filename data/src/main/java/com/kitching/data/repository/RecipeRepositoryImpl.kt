package com.kitching.data.repository

import com.kitching.data.datasource.RecipeDataSource
import com.kitching.data.datasource.impl.RecipeDataSourceImpl
import com.kitching.domain.entities.Recipe
import com.kitching.domain.repository.RecipeRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource = RecipeDataSourceImpl()
) : RecipeRepository {
    override fun getRecipes(teamId: String): Flow<AppResult<List<Recipe>>> = flow {
        emit(AppResult.Loading)

        try {
            val recipes = dataSource.getRecipes(teamId).map { it.toDomain() }

            emit(AppResult.Success(recipes))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }.catch { exception ->
        emit(AppResult.Failure(exception))
    }
}