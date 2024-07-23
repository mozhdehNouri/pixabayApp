package com.example.pixabayapp.features.picture.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.core.utils.emitToSharedFlow
import com.example.pixabayapp.features.picture.data.remote.PictureRepository
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoDataActions
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoDataState
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoUIActions
import com.example.pixabayapp.features.picture.ui.state_and_event.PhotoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val repository: PictureRepository
) : ViewModel() {

    val radioOptionsList = listOf(
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


    var photoUIState =
        MutableStateFlow<PhotoUIState>(PhotoUIState.Nothing)
        private set

    var photoDataState =
        MutableStateFlow<PhotoDataState>(PhotoDataState.Idle)
        private set

    private var photoUIActions =
        MutableStateFlow<PhotoUIActions>(
            PhotoUIActions.UpdateCategory(
                radioOptionsList[0]
            )
        )

    private var photoDataActions =
        MutableSharedFlow<PhotoDataActions>()

    private var queryString by mutableStateOf<String>("")

    init {
        manageUIRelatedActions()
        manageDataRelatedActions()
    }


    private fun manageUIRelatedActions() = viewModelScope.launch {
        photoUIActions.collectLatest { action ->
            when (action) {
                is PhotoUIActions.UpdateCategory -> {
                    queryString = action.category
                    photoUIState.update {
                        PhotoUIState.LastSelectedQuery(queryString)
                    }
                }
            }
        }
    }

    private fun manageDataRelatedActions() = viewModelScope.launch {
        photoDataActions.collectLatest { action ->
            when (action) {
                is PhotoDataActions.TryAgain -> {
                    getPhotoFromNetwork(queryString)
                }

                is PhotoDataActions.GetPhoto -> {
                    getPhotoFromNetwork(queryString)
                }

                PhotoDataActions.Nothing -> {

                }
            }
        }

    }

    private fun getPhotoFromNetwork(query: String) =
        viewModelScope.launch {
            photoDataState.update {
                PhotoDataState.Loading
            }
            when (val result = repository.getPicture(query)) {
                is AppResult.Success -> {
                    photoDataState.update {
                        PhotoDataState.PhotoSearchResult(
                            result.data?.hits ?: emptyList()
                        )
                    }
                }

                is AppResult.Error -> {
                    photoDataState.update {
                        PhotoDataState.Error(result.error.toString())
                    }
                }
            }
        }

    fun updateUIAction(actions: PhotoUIActions) {
        photoUIActions.update {
            actions
        }
    }

    fun updateDataAction(actions: PhotoDataActions) {
        emitToSharedFlow(photoDataActions, actions)
    }
}