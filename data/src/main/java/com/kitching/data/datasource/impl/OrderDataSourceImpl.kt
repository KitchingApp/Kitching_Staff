package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.core.exception.ExceptionHandler
import com.kitching.data.datasource.OrderDataSource
import com.kitching.data.dto.OrderCategoryDTO
import com.kitching.data.dto.OrderDTO
import com.kitching.data.firebase.COLLECTION_ORDER
import com.kitching.data.firebase.COLLECTION_ORDER_CATEGORY
import com.kitching.data.firebase.DOCUMENT_TEAM_ID
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : OrderDataSource {
    override suspend fun getOrderCategories(teamId: String): List<OrderCategoryDTO> = ExceptionHandler.safeCall {
            db.collection(COLLECTION_ORDER_CATEGORY)
                .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
                .get()
                .await()
                .toObjects(OrderCategoryDTO::class.java)
        }

    override suspend fun getOrderItems(teamId: String): List<OrderDTO> = ExceptionHandler.safeCall {
        db.collection(COLLECTION_ORDER)
            .whereEqualTo(DOCUMENT_TEAM_ID, teamId)
            .get()
            .await()
            .toObjects(OrderDTO::class.java)
    }
}