package com.kitching.login

import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User

enum class SplashEntryPoint {
    LOGIN,
    TEAM_SELECT,
    MAIN
}

data class SplashResult(
    val entryPoint: SplashEntryPoint,
    val user: User? = null,
    val team: Team? = null
)