package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.view.recipe.RecipeTabScreen

fun NavGraphBuilder.recipeNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation(
        route = ScreenRouteDef.BottomTab.RecipeGraph.routeName,
        startDestination = ScreenRouteDef.RecipeTab.RecipeMain.routeName
    ) {
        composable(ScreenRouteDef.RecipeTab.RecipeMain.routeName) {
            RecipeTabScreen(commonState)
        }
    }
}