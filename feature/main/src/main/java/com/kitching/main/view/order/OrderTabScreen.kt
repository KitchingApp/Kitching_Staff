package com.kitching.main.view.order

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.kitching.core.common.appresultscreen.CombinedUiStateHandler
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.Order
import com.kitching.main.R
import com.kitching.main.view.model.OrderViewModel
import com.kitching.main.view.order.carditem.CategoryCardItem
import com.kitching.main.view.order.carditem.OrderItemsList
import kotlinx.coroutines.launch

@Composable
fun OrderTabScreen(
    context: Context,
    commonState: CommonState,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val teamId = commonState.appInfoState.value.teamInfo?.teamId.toString()

    val orderCategoriesState by viewModel.orderCategories.collectAsStateWithLifecycle()
    val orderItemsState by viewModel.orderItems.collectAsStateWithLifecycle()

    val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }
    val orderCounts = remember { mutableStateMapOf<String, Int>() }

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

    LaunchedEffect(teamId) {
        viewModel.fetchOrderCategories(teamId)
        viewModel.fetchOrderItems(teamId)
    }

    KitchingStaffTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .defaultHorizontalPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.order_main_text_vertical_padding)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.order_main_text),
                    style = MaterialTheme.typography.displaySmall.copy(color = NeutralGray800)
                )

                IconButton(
                    modifier = Modifier
                        .size(
                            dimensionResource(R.dimen.order_icon_copy_size)
                        ),
                    onClick = {
                        when {
                            orderItemsState.isSuccess -> {
                                orderItemsState.data?.let { orderItems ->
                                    val orderText = generateOrderText(orderItems, orderCounts)
                                    copyToClipboard(context, orderText)
                                    Toast.makeText(context, "발주내역이 복사되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }

                            orderItemsState.isError -> {
                                Toast.makeText(context, "발주내역을 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) {
                    AsyncImage(
                        model = R.drawable.icon_copy_right,
                        contentDescription = "발주내역 복사"
                    )
                }
            }

            CombinedUiStateHandler(
                firstState = orderCategoriesState,
                secondState = orderItemsState,
                onRetry = Pair(
                    { viewModel.fetchOrderCategories(teamId) },
                    { viewModel.fetchOrderItems(teamId) }
                ),
                onSuccess = { orderCategories, orderItems ->
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.order_card_between_card)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.order_card_between_card))
                    ) {
                        items(orderCategories) { category ->
                            if (!expandedCategories.containsKey(category.categoryId)) {
                                expandedCategories[category.categoryId] = false
                            }
                            val isExpanded = expandedCategories[category.categoryId] ?: false
                            CategoryCardItem(
                                category = category,
                                isExpanded = isExpanded,
                                onCardClick = {
                                    expandedCategories[category.categoryId] = !isExpanded
                                }
                            )

                            if (isExpanded) {
                                val categoryItems = orderItems.filter { it.categoryId == category.categoryId }

                                categoryItems.forEach { order ->
                                    if (!orderCounts.containsKey(order.orderId)) {
                                        orderCounts[order.orderId] = 0
                                    }
                                }

                                OrderItemsList(
                                    orderItems = categoryItems,
                                    itemCounts = orderCounts,
                                    onIncreaseClick = { order ->
                                        orderCounts[order.orderId] = (orderCounts[order.orderId] ?: 0) + 1
                                    },
                                    onDecreaseClick = {
                                        val currentCount = orderCounts[it.orderId] ?: 0
                                        if (currentCount > 0) {
                                            orderCounts[it.orderId] = currentCount - 1
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

private fun generateOrderText(orderItems: List<Order>,orderCounts: Map<String, Int>): String {
    val orderItems = orderItems.filter { (orderCounts[it.orderId] ?: 0) > 0 }
    return buildString {
        orderItems.forEach { order ->
            val count = orderCounts[order.orderId] ?: 0
            if ( count > 0 ) {
                appendLine("${order.orderName} - $count")
            }
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("발주내역", text)
    clipboard.setPrimaryClip(clip)
}