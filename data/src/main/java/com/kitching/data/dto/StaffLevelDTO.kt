package com.kitching.data.dto

import com.kitching.domain.entities.StaffLevel

data class StaffLevelDTO(val id: String = "", val teamId: String = "", val name: String = "") {
    fun toDomain(): StaffLevel {
        return StaffLevel(
            staffLevelId = id,
            staffLevelName = name
        )
    }
}
