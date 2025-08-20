package com.kitching.core.common.navigation

import com.kitching.core.R
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material3.DrawerState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BackPressHandler(
    navController: NavController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    backPressedMessage: String = stringResource(R.string.back_pressed_message)
) {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val lastBackPressedTime = remember { mutableLongStateOf(0L) }

    DisposableEffect(Unit) {
        val callback = backPressedDispatcher?.addCallback(enabled = true) {
            val currentTime = SystemClock.elapsedRealtime()

            when {
                drawerState.isOpen -> {
                    coroutineScope.launch { drawerState.close() }
                }

                navController.previousBackStackEntry != null -> {
                    navController.popBackStack()
                    lastBackPressedTime.longValue = currentTime
                }

                currentTime - lastBackPressedTime.longValue < 3000L -> {
                    (navController.context as? ComponentActivity)?.finish()
                }

                else -> {
                    lastBackPressedTime.longValue = currentTime
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = backPressedMessage,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }

        onDispose {
            callback?.remove()
        }
    }
}