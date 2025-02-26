package com.kitching.data.dto

import com.kitching.domain.entities.PrepCategory

data class PrepCategoryDTO(
    val id: String = "",
    val teamId: String = "",
    val name: String = "",
    val color: String = ""
) {
    fun toDomain(): PrepCategory {
        return PrepCategory(
            categoryId = id,
            categoryName = name,
            color = color
        )
    }
}