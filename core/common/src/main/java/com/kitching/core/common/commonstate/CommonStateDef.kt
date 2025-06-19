package com.kitching.core.common.commonstate

import androidx.compose.material3.DrawerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.kitching.core.common.R
import com.kitching.core.designsystem.theme.NeutralGray0
import kotlinx.coroutines.CoroutineScope

enum class ActionIconInfo(val icon: Int, val description: String) {
    ADD(R.drawable.icon_add, "add button"),
    OPTION(R.drawable.icon_options, "option button"),
    CHECK(R.drawable.icon_check, "confirm button"),
    EDIT(R.drawable.icon_edit, "confirm button"),
    NULL(R.drawable.icon_check, "no action")
}

enum class NavigationIconInfo(val icon: Int, val description: String) {
    DRAWER(R.drawable.icon_hamburger_menu, "drawer icon"),
    BACK(R.drawable.icon_arrow_back, "back button")
}

data class TopAppBarState(
    val containerColor: Color = NeutralGray0,
    var title: String = "",
    val drawerState: DrawerState,
    val navIconInfo: NavigationIconInfo = NavigationIconInfo.DRAWER,
    val onClickNavIcon: () -> Unit = {},
    val actionIconInfo: ActionIconInfo = ActionIconInfo.ADD,
    val onClickActionIcon: () -> Unit = {}
)

/** 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class */
data class CommonState(
    var topAppBarState: MutableState<TopAppBarState>,
    val scope: CoroutineScope,
    val snackBarState: SnackbarHostState,
    val appInfoState: MutableState<AppInfoState>
)