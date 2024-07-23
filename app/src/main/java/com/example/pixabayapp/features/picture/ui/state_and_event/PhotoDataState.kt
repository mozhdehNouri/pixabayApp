package com.example.pixabayapp.features.picture.ui.state_and_event

import com.example.pixabayapp.features.picture.ui.data.HitPictureUIResponse

sealed interface PhotoDataState {
    data object Idle : PhotoDataState
    data object Loading : PhotoDataState
    data class Error(val message: String) : PhotoDataState
    data class PhotoSearchResult(val searchResults: List<HitPictureUIResponse>) :
        PhotoDataState
}