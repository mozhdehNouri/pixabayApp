package com.example.pixabayapp.features.picture.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PhotoScreen(modifier: Modifier = Modifier) {
    val viewmodel: PictureViewModel = hiltViewModel()
    val pictureState by viewmodel.photoUiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        items(pictureState.searchResults) { item ->
            Text(text = item.pageURL, color = Color.Black)
        }
    }

}