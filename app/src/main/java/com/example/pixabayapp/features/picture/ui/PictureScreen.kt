package com.example.pixabayapp.features.picture.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pixabayapp.R
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
    var query by remember { mutableStateOf("") }

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
        onSearchClick = {
            if (query.isNotEmpty()) {
                viewmodel.updateDataAction(PhotoDataActions.GetPhoto)
            }
        },
        isLoading = uiDataState is PhotoDataState.Loading,
        query = query,
        onQueryChange = {
            query = it
        },
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
    query: String,
    onQueryChange: (String) -> Unit,
    onOptionSelected: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val columnPadding = ColumnAllPaddingLocal.current
    val columnVerticalPadding = ColumnVerticalPaddingLocal.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(columnPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
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
        TextField(
            value = query,
            onValueChange = { onQueryChange(it) },
            modifier = Modifier.fillMaxWidth(), label = {
                Text(
                    stringResource(R.string.lbl_text_field_hint),
                    style = MaterialTheme.typography.bodySmall
                )
            }, trailingIcon = {
                Icon(Icons.Default.Search, null, Modifier.clickable { onSearchClick() })
            }, singleLine = true
        )
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
        }
        if (response.isEmpty()) {
            Text(
                stringResource(R.string.lbl_response_is_empty),
                modifier = Modifier.padding(top = 20.dp)
            )
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