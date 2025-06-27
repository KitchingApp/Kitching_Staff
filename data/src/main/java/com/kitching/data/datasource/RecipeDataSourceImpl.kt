package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.IngredientDTO
import com.kitching.data.dto.RecipeDTO
import com.kitching.data.firebase.COLLECTION_INGREDIENT
import com.kitching.data.firebase.COLLECTION_RECIPE
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import kotlinx.coroutines.tasks.await

class RecipeDataSourceImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : RecipeDataSource {
    override suspend fun getRecipes(teamId: String): List<RecipeDTO> {
        return try {
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
        } catch (e: Exception) {
            emptyList()
        }
    }
}