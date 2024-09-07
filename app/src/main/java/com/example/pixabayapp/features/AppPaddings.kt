package com.example.pixabayapp.features

import android.inputmethodservice.Keyboard.Row
import android.media.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text

// column padding
private val ColumnAllPadding = PaddingValues(8.dp)
private val ColumnVerticalPadding = PaddingValues(vertical = 8.dp)
private val ColumnHorizontalPadding = PaddingValues(horizontal = 8.dp)
val ColumnAllPaddingLocal= staticCompositionLocalOf { ColumnAllPadding }
val ColumnVerticalPaddingLocal= staticCompositionLocalOf {  ColumnVerticalPadding}
val ColumnHorizontalPaddingLocal= staticCompositionLocalOf {  ColumnHorizontalPadding}

// row padding
private val RowAllPadding = PaddingValues(4.dp)
private val RowVerticalPadding = PaddingValues(vertical = 4.dp)
private val RowHorizontalPadding = PaddingValues(horizontal = 4.dp)
val RowAllPaddingLocal= staticCompositionLocalOf { RowAllPadding }
val RowVerticalPaddingLocal= staticCompositionLocalOf {  RowVerticalPadding}
val RowHorizontalPaddingLocal= staticCompositionLocalOf {  RowHorizontalPadding}

// text padding
private val TextAllPadding = PaddingValues(2.dp)
private val TextVerticalPadding = PaddingValues(vertical = 12.dp)
private val TextHorizontalPadding = PaddingValues(horizontal = 2.dp)
val TextAllPaddingLocal= staticCompositionLocalOf { TextAllPadding }
val TextVerticalPaddingLocal= staticCompositionLocalOf {  TextVerticalPadding}
val TextHorizontalPaddingLocal= staticCompositionLocalOf {  TextHorizontalPadding}


// image or icon padding
private val ImageAllPadding = PaddingValues(2.dp)
private val ImageVerticalPadding = PaddingValues(vertical = 2.dp)
private val ImageHorizontalPadding = PaddingValues(horizontal = 2.dp)
val ImageAllPaddingLocal = staticCompositionLocalOf { ImageAllPadding }
val ImageVerticalPaddingLocal = staticCompositionLocalOf { ImageVerticalPadding }
val ImageHorizontalPaddingLocal = staticCompositionLocalOf { ImageHorizontalPadding }

