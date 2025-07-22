package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Recipe
import com.kitching.domain.repository.RecipeRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import com.kitching.domain.util.getDisplayMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _recipeList = MutableStateFlow(UiState<List<Recipe>>())
    val recipeList get() =_recipeList.asStateFlow()

    fun getRecipeListByTeamId(teamId: String) {
        viewModelScope.launch {
            repository.getRecipes(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _recipeList.value = _recipeList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _recipeList.value = _recipeList.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _recipeList.value = _recipeList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }
}