package com.kitching.core.common.commonstate

import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User

data class AppInfoState(
    val userInfo: User? = User(),
    val teamInfo: Team? = Team(),
)