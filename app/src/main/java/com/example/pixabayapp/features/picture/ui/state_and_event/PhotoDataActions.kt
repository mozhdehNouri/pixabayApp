package com.example.pixabayapp.features.picture.ui.state_and_event

// screen specific action which is more related to work with data

sealed interface PhotoDataActions {
    data object Nothing : PhotoDataActions
    data object GetPhoto : PhotoDataActions
    data object TryAgain : PhotoDataActions
}