package com.example.pixabayapp.ui.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun PaddingValues.plus(
    plus: PaddingValues,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
): PaddingValues = PaddingValues(
    start = calculateStartPadding(layoutDirection) + plus.calculateStartPadding(layoutDirection),
    top = calculateTopPadding() + plus.calculateTopPadding(),
    end = calculateEndPadding(layoutDirection) + plus.calculateEndPadding(layoutDirection),
    bottom = calculateBottomPadding() + plus.calculateBottomPadding(),
)

fun PaddingValues.minus(
    other: PaddingValues,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
): PaddingValues = PaddingValues(
    start = (calculateStartPadding(layoutDirection) - other.calculateStartPadding(layoutDirection)).coerceAtLeast(0.dp),
    top = (calculateTopPadding() - other.calculateTopPadding()).coerceAtLeast(0.dp),
    end = (calculateEndPadding(layoutDirection) - other.calculateEndPadding(layoutDirection)).coerceAtLeast(0.dp),
    bottom = (calculateBottomPadding() - other.calculateBottomPadding()).coerceAtLeast(0.dp),
)