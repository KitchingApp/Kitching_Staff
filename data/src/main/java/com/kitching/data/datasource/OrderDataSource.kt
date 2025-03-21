package com.kitching.data.datasource

import com.kitching.data.dto.OrderCategoryDTO
import com.kitching.data.dto.OrderDTO

interface OrderDataSource {
    suspend fun getOrderCategories(teamId: String): List<OrderCategoryDTO>

    suspend fun getOrderItems(teamId: String): List<OrderDTO>
}