package com.kitching.data.dto

import com.kitching.domain.entities.Prep

data class PrepDTO(
    val id: String = "",
    val categoryId: String = "",
    val teamId: String = "",
    val name: String = ""
) {
    fun toDomain(): Prep {
        return Prep(
            categoryId = categoryId,
            prepId = id,
            prepName = name
        )
    }
}