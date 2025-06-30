package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.navigation.parcelableType
import com.kitching.domain.entities.Recipe
import com.kitching.main.view.recipe.RecipeTabScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.recipeNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation(
        route = ScreenRouteDef.BottomTab.RecipeGraph.routeName,
        startDestination = ScreenRouteDef.RecipeTab.RecipeMain.routeName,
    ) {
        composable<ScreenRouteDef.RecipeTab.RecipeMain> {
            RecipeTabScreen(commonState) { recipe ->
                navController.navigate(
                    ScreenRouteDef.RecipeTab.RecipeDetail(recipe)
                )
            }
        }

        composable<ScreenRouteDef.RecipeTab.RecipeDetail>(
            typeMap = mapOf(typeOf<Recipe>() to parcelableType<Recipe>())
        ) { backStackEntry ->
            val recipe = backStackEntry.toRoute<ScreenRouteDef.RecipeTab.RecipeDetail>()

        }
    }
}