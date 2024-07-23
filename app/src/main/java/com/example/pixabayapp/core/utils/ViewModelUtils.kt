package com.example.pixabayapp.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

context(ViewModel)
fun <T> emitToSharedFlow(sharedFlow: MutableSharedFlow<T>, value: T) {
    viewModelScope.launch {
        sharedFlow.emit(value)
    }
}