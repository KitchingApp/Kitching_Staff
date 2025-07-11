package com.kitching.main.view.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.core.common.appresultscreen.ProgressIndicatorScreen
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.defaultHorizontalPadding
import com.kitching.domain.entities.Notice
import com.kitching.domain.util.AppResult
import com.kitching.main.R
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.view.model.OtherViewModel
import com.kitching.main.view.other.detailsection.CommentLine
import com.kitching.main.view.other.detailsection.CommentTextField
import com.kitching.main.view.other.detailsection.NoticeContentSection
import com.kitching.main.view.other.item.CommentsItem

@Composable
fun NoticeDetailScreen(
    commonState: CommonState,
    notice: Notice,
    viewModel: OtherViewModel = viewModel(factory = viewModelFactory),
    onBack: () -> Unit
) {
    val user = commonState.appInfoState.value.userInfo!!

    var commentText by remember { mutableStateOf("") }
    var useNotice by remember { mutableStateOf<Notice>(notice) }

    val addCommentFailMessage = stringResource(R.string.other_add_comment_fail)

    val addCommentResult by viewModel.addCommentResult.collectAsStateWithLifecycle()
    val noticeByIdResult by viewModel.noticeByIdResult.collectAsStateWithLifecycle()

    val isLoading = addCommentResult is AppResult.Loading || noticeByIdResult is AppResult.Loading

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = stringResource(R.string.other_notice_title),
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = onBack,
        actionIconInfo = ActionIconInfo.NULL,
    )

    LaunchedEffect(addCommentResult) {
        when (addCommentResult) {
            is AppResult.Success -> {
                commentText = ""
                viewModel.getNoticeById(useNotice.noticeId)
                viewModel.resetAddCommentResult()
            }
            is AppResult.Failure -> {
                viewModel.resetAddCommentResult()
                commonState.snackBarState.showSnackbar(addCommentFailMessage)
            }
            else -> {}
        }
    }

    LaunchedEffect(noticeByIdResult) {
        when (noticeByIdResult) {
            is AppResult.Success -> {
                val newNotice = (noticeByIdResult as AppResult.Success<Notice>).data
                useNotice = newNotice
            }

            else -> {}
        }
    }

    KitchingStaffTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NoticeContentSection(useNotice)

            CommentLine()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultHorizontalPadding()
                    .weight(1f)
                    .padding(vertical = KitchingDimens.Margin.small),
                verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.xxSmall)
            ) {
                items(
                    items = useNotice.comments,
                    key = { it.id }
                ) { comment ->
                    CommentsItem(comment)
                }
            }

            CommentTextField(
                modifier = Modifier.imePadding(),
                commentText = commentText,
                onCommentTextChange = { commentText = it },
            ) {
                viewModel.addComment(useNotice.noticeId, user, commentText)
            }
        }

        if (isLoading) {
            ProgressIndicatorScreen()
        }
    }
}