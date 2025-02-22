package com.kitching.data.dto

import com.kitching.domain.entities.OrderCategory

data class OrderCategoryDTO(
    val id: String = "",
    val teamId: String = "",
    val name: String = "",
    val color: String = ""
) {
    fun toDomain(): OrderCategory {
        return OrderCategory(
            categoryId = id,
            categoryName = name,
            color = color
        )
    }
}
