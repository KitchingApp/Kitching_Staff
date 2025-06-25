package com.kitching.main.view.recipe

import androidx.compose.runtime.Composable
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.theme.NeutralGray0
import kotlinx.coroutines.launch

@Composable
fun RecipeTabScreen(
    commonState: CommonState,
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = commonState.appInfoState.value.teamInfo?.teamName ?: "",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.NULL,
    )

}