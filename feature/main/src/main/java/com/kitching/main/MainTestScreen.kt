package com.kitching.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kitching.core.common.ActionIconInfo
import com.kitching.core.common.CommonState
import com.kitching.core.common.TopAppBarState
import com.kitching.main.navigation.CustomNavHost
import com.kitching.core.common.CustomNavigationBar
import com.kitching.core.common.CustomNavigationDrawer
import com.kitching.core.common.CustomTopAppBar
import com.kitching.core.common.NavigationIconInfo
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.PrimaryGreen300
import kotlinx.coroutines.launch

@Composable
fun EntryPointScreen(
    appNavController: NavHostController
) {
    val tabNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Kitching") } // 레스토랑 이름
    val topAppBarState = remember { mutableStateOf(TopAppBarState(drawerState = drawerState, title = title)) }
    val commonState by remember { mutableStateOf(
        CommonState(navController = tabNavController, topAppBarState = topAppBarState, scope = scope)
    ) }

    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    CustomNavigationDrawer(
        drawerState, onLogout = {
            appNavController.navigate(ScreenRouteDef.Splash.routeName) {
                popUpTo(ScreenRouteDef.MainGraph.routeName) {
                    inclusive = true
                }
            }
        }) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CustomTopAppBar(
                topAppBarState = topAppBarState.value
            ) },
            bottomBar = { CustomNavigationBar(
                navController = tabNavController,
                currentDestination = currentDestination
            ) }
        ) { paddingValues -> CustomNavHost(
            navController = tabNavController,
            paddingValues = paddingValues,
            commonState = commonState
        ) }
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
fun ScheduleTabScreen(
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = PrimaryGreen300,
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

    Text("ScheduleTabScreen")
}

@Composable
fun OrderTabScreen(
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

    Text("OrderTabScreen")
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