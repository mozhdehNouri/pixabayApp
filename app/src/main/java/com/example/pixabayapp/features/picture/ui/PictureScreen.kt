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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pixabayapp.features.picture.ui.data.HitPictureUIResponse
import com.example.pixabayapp.features.picture.ui.data.PictureUiEvent

@Composable
fun PhotoScreen() {
    val viewmodel: PictureViewModel = hiltViewModel()
    val uiState by viewmodel.photoUiState.collectAsStateWithLifecycle()
    val radioOptions = listOf(
        "animal",
        "background",
        "flowers",
        "woman",
        "money",
        "sport",
        "house",
        "travel",
        "people"
    )
    var selectedOption by remember {
        mutableStateOf(
            radioOptions[0]
        )
    }
    LaunchedEffect(key1 = Unit) {
        viewmodel.reducer(PictureUiEvent.UpdateQuery(radioOptions[0]))
    }

    PictureUI(
        radioOptionListItem = radioOptions,
        selectedOption = selectedOption,
        onOptionSelected = { item ->
            selectedOption = item
            viewmodel.reducer(PictureUiEvent.UpdateQuery(selectedOption))
        },
        isLoading = uiState.loading,
        response = uiState.searchResults
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        FlowRow(Modifier.fillMaxWidth()) {
            radioOptionListItem.forEach { item ->
                RadioItem(
                    text = item,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedItem ->
                        onOptionSelected(selectedItem)
                    })
            }
        }

        Spacer(modifier = Modifier.padding(top = 20.dp))
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
private fun RadioItem(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 2.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = LightGray,
                shape = shape
            )
            .background(
                color = LightGray,
                shape = shape
            )
            .clip(shape = shape)
            .padding(4.dp)
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
            text = text, style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}