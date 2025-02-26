package com.kitching.data.dto

import com.kitching.domain.entities.Order

data class OrderDTO(
    val id: String = "",
    val categoryId: String = "",
    val name: String = ""
) {
    fun toDomain(): Order {
        return Order(
            orderId = id,
            orderName = name,
            categoryId = categoryId
        )
    }
}
