package com.kitching.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.widget.CustomNavigationBar
import com.kitching.core.common.CustomTopAppBar
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.main.navigation.CustomNavHost
import com.kitching.main.view.drawer.CustomDrawer
import kotlinx.coroutines.launch

@Composable
fun EntryPointScreen(
    appNavController: NavHostController,
    commonState: CommonState,
) {

    val tabNavController = rememberNavController()

    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    CustomDrawer(
        drawerState = commonState.topAppBarState.value.drawerState,
        commonState = commonState,
        context = appNavController.context,
        onInviteCodeClick = {
            tabNavController.navigate(ScreenRouteDef.Other.InviteCode)
        },
        onNoticeClick = {  },
        onMemberClick = {
            tabNavController.navigate(ScreenRouteDef.Other.MemberList)
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(
                    topAppBarState = commonState.topAppBarState.value
                )
            },
            bottomBar = {
                CustomNavigationBar(
                    navController = tabNavController,
                    currentDestination = currentDestination
                )
            }
        ) { paddingValues ->
            CustomNavHost(
                navController = tabNavController,
                paddingValues = paddingValues,
                commonState = commonState
            )
        }
    }
}

@Composable
fun ChatTabScreen(
    commonState: CommonState,
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.NULL
    )
    Spacer(modifier = Modifier.height(50.dp))

    Text("ChatTabScreen")
}