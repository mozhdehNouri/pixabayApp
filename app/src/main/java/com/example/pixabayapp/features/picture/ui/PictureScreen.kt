package com.example.pixabayapp.features.picture.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pixabayapp.features.ColumnAllPaddingLocal
import com.example.pixabayapp.features.ColumnVerticalPaddingLocal
import com.example.pixabayapp.features.RowAllPaddingLocal
import com.example.pixabayapp.features.TextHorizontalPaddingLocal
import com.example.pixabayapp.features.picture.ui.data.HitPictureUIResponse
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoDataActions
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoDataState
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoUIActions
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoUIState

@Composable
fun PhotoScreen() {
    val viewmodel: PictureViewModel = hiltViewModel()
    val uiState by viewmodel.photoUIState.collectAsStateWithLifecycle()
    val uiDataState by viewmodel.photoDataState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.updateDataAction(PhotoDataActions.GetPhoto)
    }

    PictureUI(
        radioOptionListItem = viewmodel.radioOptionsList,
        selectedOption = (uiState as? PhotoUIState.LastSelectedQuery)?.query
            ?: "",
        onOptionSelected = { item ->
            viewmodel.updateUIAction(PhotoUIActions.UpdateCategory(item))
            viewmodel.updateDataAction(PhotoDataActions.GetPhoto)
        },
        isLoading = uiDataState is PhotoDataState.Loading,
        response = (uiDataState as? PhotoDataState.PhotoSearchResult)?.searchResults
            ?: emptyList()
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PictureUI(
    radioOptionListItem: List<String>,
    response: List<HitPictureUIResponse>,
    selectedOption: String,
    isLoading: Boolean,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val columnPadding = ColumnAllPaddingLocal.current
    val columnVerticalPadding = ColumnVerticalPaddingLocal.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(columnPadding)
    ) {
        FlowRow(Modifier.fillMaxWidth()) {
            radioOptionListItem.forEach { item ->
                SelectedOption(
                    text = item,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedItem ->
                        onOptionSelected(selectedItem)
                    })
            }
        }
        Spacer(modifier = Modifier.padding(columnVerticalPadding))
        if (isLoading) {
            CircularProgressIndicator()
        }

        LazyColumn {
            items(response, key = { it.id }) { item ->
                PhotoListItem(item)
            }
        }
    }
}

@Composable
private fun SelectedOption(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = MaterialTheme.shapes.small
    val rowPadding = RowAllPaddingLocal.current
    val textPadding = TextHorizontalPaddingLocal.current
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                rowPadding
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = shape
            )
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = shape
            )
            .clip(shape = shape)
            .padding(rowPadding)
            .selectable(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) },
                role = Role.RadioButton
            )
    ) {
        if (text == selectedOption) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = DarkGray
            )
        }
        Text(
            text = text, style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(textPadding),
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}