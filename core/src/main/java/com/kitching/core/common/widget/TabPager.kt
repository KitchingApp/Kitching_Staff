package com.kitching.core.common.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.core.designsystem.SecondaryLightGreen100
import kotlinx.coroutines.launch

@Composable
fun TabPager(
    tabs: List<TabItem>,
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    tabBackgroundSelected: Color = SecondaryLightGreen100,
    tabBackgroundUnselected: Color = Color.White,
    indicatorColor: Color = PrimaryGreen300,
    pagerState: PagerState = rememberPagerState(initialPage = initialPage) { tabs.size }
) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        // 탭 레이아웃
        TabLayout(
            tabs = tabs,
            selectedTabIndex = pagerState.currentPage,
            onTabSelected = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
            tabBackgroundSelected = tabBackgroundSelected,
            tabBackgroundUnselected = tabBackgroundUnselected,
            indicatorColor = indicatorColor
        )

        // 컨텐츠 페이저
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            tabs[page].content()
        }
    }
}