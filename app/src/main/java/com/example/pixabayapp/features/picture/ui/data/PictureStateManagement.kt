package com.example.pixabayapp.features.picture.ui.data

import androidx.compose.runtime.Immutable

@Immutable
data class PictureUiState(
    val query: String = "",
    val searchResults: List<HitPictureUIResponse> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val eventSink: (PictureUiEvent) -> Unit={}
)

sealed interface PictureUiEvent {
    data object ClearMessage: PictureUiEvent
    data class UpdateQuery(val query: String) : PictureUiEvent
    data class OpenShowDetails(val showId: Long) : PictureUiEvent
}