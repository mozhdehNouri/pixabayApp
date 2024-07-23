package com.example.pixabayapp.features.picture.ui.state_and_event

sealed interface PhotoUIState {
    data object Nothing : PhotoUIState
    data class LastSelectedQuery(val query: String) : PhotoUIState
}

enum class DialogType {
    Loading,
    Continue
}