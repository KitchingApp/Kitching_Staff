package com.kitching.core.common

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User

@Composable
fun rememberCommonState(): CommonState {
    val scope = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val topAppBarState = remember { mutableStateOf(TopAppBarState(drawerState = drawerState)) }
    val appInfoState = remember { mutableStateOf(AppInfoState()) }

    return remember {
        CommonState(
            topAppBarState = topAppBarState,
            scope = scope,
            snackBarState = snackBarState,
            appInfoState = appInfoState
        )
    }
}

fun CommonState.updateUserInfo(user: User?) {
    appInfoState.value = appInfoState.value.copy(userInfo = user)
}

fun CommonState.updateTeamInfo(team: Team) {
    appInfoState.value = appInfoState.value.copy(teamInfo = team)
}

fun CommonState.updateAppInfo(user: User?, team: Team?) {
    appInfoState.value = appInfoState.value.copy(userInfo = user, teamInfo = team)
}

fun CommonState.clearAppInfo() {
    appInfoState.value = AppInfoState()
}