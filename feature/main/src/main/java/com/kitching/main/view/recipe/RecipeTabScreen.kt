package com.kitching.main.view.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kitching.core.common.appresultscreen.UiStateHandler
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.common.widget.SearchTextField
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.Recipe
import com.kitching.main.R
import com.kitching.main.view.model.RecipeViewModel
import com.kitching.main.view.recipe.list.RecipeGridList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RecipeTabScreen(
    commonState: CommonState,
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeClick: (Recipe) -> Unit,
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = commonState.appInfoState.value.teamInfo?.teamName ?: "",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.NULL,
    )

    val teamId = commonState.appInfoState.value.teamInfo?.teamId.toString()
    val recipeListState by viewModel.recipeList.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }
    var debouncedSearchQuery by remember { mutableStateOf("") }

    LaunchedEffect(teamId) {
        viewModel.getRecipeListByTeamId(teamId)
    }

    LaunchedEffect(searchQuery) {
        delay(500)
        debouncedSearchQuery = searchQuery
    }

    KitchingStaffTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = KitchingDimens.Margin.large)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultHorizontalPadding()
                    .padding(bottom = KitchingDimens.Margin.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = KitchingDimens.Margin.medium),
                    text = stringResource(id = R.string.recipe_search_title),
                    style = MaterialTheme.typography.displaySmall.copy(color = NeutralGray800),
                )

                SearchTextField(
                    searchQuery,
                    onSearchQueryChange = {
                        searchQuery = it
                    }
                ) {
                    debouncedSearchQuery = searchQuery
                }
            }

            UiStateHandler(
                uiState = recipeListState,
                onRetry = { viewModel.getRecipeListByTeamId(teamId) }
            ) { recipeList ->
                val filteredRecipes = remember(recipeList, debouncedSearchQuery) {
                    if (debouncedSearchQuery.isEmpty()) {
                        recipeList
                    } else {
                        recipeList.filter { recipe ->
                            recipe.recipeName.contains(debouncedSearchQuery, ignoreCase = true)
                        }
                    }
                }

                RecipeGridList(filteredRecipes) { recipe ->
                    onRecipeClick(recipe)
                }
            }
        }
    }
}