package com.kitching.main.view.prep.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import com.kitching.main.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.graphics.toColorInt
import com.kitching.core.common.widget.CommonDialogComponent
import com.kitching.core.designsystem.theme.Body1_m
import com.kitching.core.designsystem.theme.Caption1_m
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray500
import com.kitching.core.designsystem.theme.NeutralGray600
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.domain.entities.Prep
import com.kitching.domain.entities.PrepCategory

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TodoPrepDialog(
    onDismiss: () -> Unit,
    onConfirm: (categoryId: String, todoId: String) -> Unit,
    selectedDate: String,
    prepCategories: List<PrepCategory>,
    preps: List<Prep>,
) {
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    var selectedPrepId by remember { mutableStateOf<String?>(null) }
    var isPrepDropdownExpanded by remember { mutableStateOf(false) }

    val filteredPreps = remember(selectedCategoryId, preps) {
        if (selectedCategoryId != null) {
            preps.filter { it.categoryId == selectedCategoryId }
        } else {
            emptyList()
        }
    }

    val selectedPrep = remember(selectedPrepId, filteredPreps) {
        filteredPreps.find { it.prepId == selectedPrepId }
    }

    val isValidSelection = selectedCategoryId != null && selectedPrepId != null

    CommonDialogComponent(
        height = dimensionResource(R.dimen.prep_dialog_height),
        paddingTop = KitchingDimens.Margin.xLarge,
        paddingBottom = KitchingDimens.Margin.xxLarge,
        radius = KitchingDimens.Corner.large,
        confirmText = stringResource(R.string.prep_dialog_confirm_text),
        onClickConfirm = {
            if (isValidSelection) {
                onConfirm(selectedCategoryId!!, selectedPrepId!!)
            }
        },
        cancelText = stringResource(R.string.prep_dialog_cancel_text),
        onClickCancel = onDismiss,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.medium)
        ) {
            Text(
                text = selectedDate,
                style = Body1_m.copy(NeutralGray800),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.xxSmall),
                contentPadding = PaddingValues(horizontal = KitchingDimens.Margin.xxSmall),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = prepCategories,
                    key = { it.categoryId }
                ) { category ->
                    FilterChip(
                        onClick = {
                            if (selectedCategoryId == category.categoryId) {
                                selectedCategoryId = null
                                selectedPrepId = null
                                isPrepDropdownExpanded = false
                            } else {
                                selectedCategoryId = category.categoryId
                                selectedPrepId = null
                                isPrepDropdownExpanded = false
                            }
                        },
                        label = {
                            Text(
                                text = category.categoryName,
                                style = Body1_m
                            )
                        },
                        selected = selectedCategoryId == category.categoryId,
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color(category.color.toColorInt()),
                            selectedContainerColor = PrimaryGreen300,
                            labelColor = NeutralGray800,
                            selectedLabelColor = NeutralGray0
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedCategoryId == category.categoryId,
                            borderColor = Color(category.color.toColorInt()),
                            selectedBorderColor = PrimaryGreen300,
                            borderWidth = KitchingDimens.Border.xxxSmall
                        )
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                BoxWithConstraints {
                    OutlinedButton(
                        onClick = {
                            if (selectedCategoryId != null && filteredPreps.isNotEmpty()) {
                                isPrepDropdownExpanded = !isPrepDropdownExpanded
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.prep_dialog_dropdown_button_height)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = NeutralGray0,
                            contentColor = NeutralGray800
                        ),
                        border = BorderStroke(
                            width = KitchingDimens.Border.xxxSmall,
                            color = NeutralGray500
                        ),
                        shape = RoundedCornerShape(KitchingDimens.Corner.xSmall)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when {
                                    selectedCategoryId == null -> stringResource(R.string.prep_dialog_not_choose_category)
                                    filteredPreps.isEmpty() -> stringResource(R.string.prep_dialog_empty_prep_in_category)
                                    selectedPrep != null -> selectedPrep.prepName
                                    else -> stringResource(R.string.prep_dialog_choice_prep)
                                },
                                style = Caption1_m.copy(
                                    color = when {
                                        selectedCategoryId == null || filteredPreps.isEmpty() -> NeutralGray500
                                        selectedPrep != null -> NeutralGray800
                                        else -> NeutralGray500
                                    }
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            if (selectedCategoryId != null && filteredPreps.isNotEmpty()) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        if (isPrepDropdownExpanded) R.drawable.icon_arrow_up
                                        else R.drawable.icon_arrow_down
                                    ),
                                    contentDescription = null,
                                    tint = NeutralGray600,
                                    modifier = Modifier.size(KitchingDimens.Size.medium)
                                )
                            }
                        }
                    }

                    DropdownMenu(
                        expanded = isPrepDropdownExpanded,
                        onDismissRequest = { isPrepDropdownExpanded = false },
                        modifier = Modifier
                            .width(maxWidth)
                            .heightIn(max = dimensionResource(R.dimen.prep_dialog_dropdown_item_height)),
                        containerColor = NeutralGray0
                    ) {
                        filteredPreps.forEach { prep ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = {
                                    Text(
                                        text = prep.prepName,
                                        style = Caption1_m.copy(color = NeutralGray800)
                                    )
                                },
                                onClick = {
                                    selectedPrepId = prep.prepId
                                    isPrepDropdownExpanded = false
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}