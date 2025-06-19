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
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.util.AppResult
import com.kitching.main.navigation.CustomNavHost
import com.kitching.main.view.drawer.CustomDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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
fun PrepTabScreen(
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "Kitching",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {

        },
    )
    Spacer(modifier = Modifier.height(50.dp))

    Text("PrepTabScreen")
}

@Composable
fun RecipeTabScreen(
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "Kitching",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {

        }
    )
    Spacer(modifier = Modifier.height(50.dp))

    Text("RecipeTabScreen")
}

@Composable
fun ChatTabScreen(
    commonState: CommonState
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