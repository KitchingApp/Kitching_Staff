package com.kitching.main.order

import androidx.compose.foundation.layout.Arrangement
import com.kitching.main.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.core.common.ActionIconInfo
import com.kitching.core.common.AppResultHandler
import com.kitching.core.common.CombinedAppResultHandler
import com.kitching.core.common.CommonState
import com.kitching.core.common.NavigationIconInfo
import com.kitching.core.designsystem.theme.H2
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.defaultHorizontalPadding
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.order.carditem.CategoryCardItem
import com.kitching.main.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun OrderTabScreen(
    commonState: CommonState,
    viewModel: OrderViewModel = viewModel(factory = viewModelFactory),
) {
    val teamId = "3uM01g5GSz8lC49JA6vq"

    val orderCategoriesState by viewModel.orderCategories.collectAsStateWithLifecycle()
    val orderItemsState by viewModel.orderItems.collectAsStateWithLifecycle()

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
        actionIconInfo = ActionIconInfo.NULL,
        onClickActionIcon = {

        },
    )

    LaunchedEffect(Unit) {
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
                    style = H2.copy(color = NeutralGray800)
                )

                IconButton(
                    modifier = Modifier
                        .size(
                            dimensionResource(R.dimen.order_icon_copy_size)
                        ),
                    onClick = {}
                ) {
                    AsyncImage(
                        model = R.drawable.icon_copy_right,
                        contentDescription = "발주내역 복사"
                    )
                }
            }

            CombinedAppResultHandler(
                firstState = orderCategoriesState,
                secondState = orderItemsState,
                onSuccess = { orderCategories, orderItems ->
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.order_card_between_card))
                    ) {
                        items(orderCategories) { category ->
                            CategoryCardItem(
                                category = category,
                                isExpanded = false,
                                onCardClick = {}
                            )
                        }
                    }
                }
            )
        }
    }
}