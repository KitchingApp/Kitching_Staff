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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kitching.core.common.appresultscreen.ProgressIndicatorScreen
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.Notice
import com.kitching.main.R
import com.kitching.main.view.model.OtherViewModel
import com.kitching.main.view.other.detailsection.CommentLine
import com.kitching.main.view.other.detailsection.CommentTextField
import com.kitching.main.view.other.detailsection.NoticeContentSection
import com.kitching.main.view.other.dialog.DeleteCommentDialog
import com.kitching.main.view.other.item.CommentsItem

@Composable
fun NoticeDetailScreen(
    commonState: CommonState,
    notice: Notice,
    viewModel: OtherViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val user = commonState.appInfoState.value.userInfo!!

    var commentText by remember { mutableStateOf("") }
    var useNotice by remember { mutableStateOf<Notice>(notice) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteCommentId by remember { mutableStateOf("") }

    val noticeByIdResult by viewModel.noticeByIdResult.collectAsStateWithLifecycle()
    val commentActonResult by viewModel.commentAction.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = stringResource(R.string.other_notice_title),
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = onBack,
        actionIconInfo = ActionIconInfo.NULL,
    )

    LaunchedEffect(commentActonResult) {
        when {
            commentActonResult.isSuccess -> {
                commentText = ""
                viewModel.getNoticeById(useNotice.noticeId)
            }
            commentActonResult.isError -> {
                commonState.snackBarState.showSnackbar(commentActonResult.error.toString())
            }
        }
    }

    LaunchedEffect(noticeByIdResult) {
        when {
            noticeByIdResult.isSuccess -> {
                noticeByIdResult.data?.let { updateNotice ->
                    useNotice = updateNotice
                }
            }
            noticeByIdResult.isError -> {
                commonState.snackBarState.showSnackbar(noticeByIdResult.error.toString())
            }
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
                    CommentsItem(
                        userId = user.userId,
                        comment = comment
                    ) { commentId ->
                        deleteCommentId = commentId
                        showDeleteDialog = true
                    }
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

        if (showDeleteDialog) {
            DeleteCommentDialog(
                onDismiss = {
                    showDeleteDialog = false
                    deleteCommentId = ""
                },
                onConfirm = {
                    viewModel.deleteComment(useNotice.noticeId, deleteCommentId)
                    deleteCommentId = ""
                }
            )
        }

        if (commentActonResult.isLoading) {
            ProgressIndicatorScreen()
        }
    }
}