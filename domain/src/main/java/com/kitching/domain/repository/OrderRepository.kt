package com.kitching.domain.repository

import com.kitching.domain.entities.Order
import com.kitching.domain.entities.OrderCategory
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getOrderCategories(teamId: String): Flow<AppResult<List<OrderCategory>>>

    suspend fun getOrderItems(teamId: String): Flow<AppResult<List<Order>>>
}