package com.kitching.domain.repository

import com.kitching.domain.entities.Order
import com.kitching.domain.entities.OrderCategory
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrderCategories(teamId: String): Flow<AppResult<List<OrderCategory>>>

    fun getOrderItems(teamId: String): Flow<AppResult<List<Order>>>
}