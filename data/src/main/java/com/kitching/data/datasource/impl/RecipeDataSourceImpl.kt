package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.core.exception.ExceptionHandler
import com.kitching.data.datasource.RecipeDataSource
import com.kitching.data.dto.IngredientDTO
import com.kitching.data.dto.RecipeDTO
import com.kitching.data.firebase.COLLECTION_INGREDIENT
import com.kitching.data.firebase.COLLECTION_RECIPE
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : RecipeDataSource {
    override suspend fun getRecipes(teamId: String): List<RecipeDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_RECIPE)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .documents
            .mapNotNull { documentSnapshot ->
                val recipeDTO = documentSnapshot.toObject(RecipeDTO::class.java)

                val ingredients = documentSnapshot.reference
                    .collection(COLLECTION_INGREDIENT)
                    .get()
                    .await()
                    .toObjects(IngredientDTO::class.java)

                recipeDTO?.copy(ingredient = ingredients)
            }
    }
}