package com.example.pixabayapp.features.picture.ui.state_and_event

// screen specific action which is more related to work with data

sealed interface PhotoUIActions {
    //    data object Nothing : PhotoUIActions
    data class UpdateCategory(val category: String) : PhotoUIActions
//    data class ShowDialog() : PhotoUIActions
//data class ShowDialog(val dialogType: DialogType) :
//    PhotoUIActions
}