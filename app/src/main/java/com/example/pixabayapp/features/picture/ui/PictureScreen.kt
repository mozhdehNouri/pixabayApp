package com.example.pixabayapp.features.picture.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.pixabayapp.features.picture.ui.data.HitPictureUIResponse
import com.example.pixabayapp.features.picture.ui.data.PictureUiEvent

@Composable
fun PhotoScreen(modifier: Modifier = Modifier) {
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
        itemList = radioOptions,
        selectedOption = selectedOption,
        onOptionSelected = { item ->
            selectedOption = item
            viewmodel.reducer(PictureUiEvent.UpdateQuery(selectedOption))
        },
        isLoading = uiState.loading,
        response = uiState.searchResults
    )

}

@Composable
fun PictureUI(
    itemList: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isLoading: Boolean,
    response: List<HitPictureUIResponse>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        itemList.forEach { item ->
            RadioItem(
                text = item,
                selectedOption = selectedOption,
                onOptionSelected = { selectedItem ->
                    onOptionSelected(selectedItem)
                })
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        if (isLoading) {
            CircularProgressIndicator()
        }

        LazyColumn {
            items(response, key = { it.id }) { item ->
                PictureItem(item)
            }
        }
    }
}

@Composable
fun RadioItem(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .selectable(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = (text == selectedOption), onClick = null)
        Text(
            text = text, style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun PictureItem(
    item: HitPictureUIResponse,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = item.largeImageURL,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = modifier.clip(MaterialTheme.shapes.small)
            )
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(20.dp)
                    .background(
                        Color.Red
                    )
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = null
                )
            }
        }
    }
}