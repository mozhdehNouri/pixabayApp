package com.example.pixabayapp.features.picture.ui.state_and_event

// screen specific action which is more related to work with data

sealed interface PhotoUIActions {
    data class UpdateCategory(val category: String) : PhotoUIActions
}