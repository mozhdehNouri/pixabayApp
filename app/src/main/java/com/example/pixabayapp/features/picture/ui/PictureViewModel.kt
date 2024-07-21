package com.example.pixabayapp.features.picture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.features.picture.data.remote.PictureRepository
import com.example.pixabayapp.features.picture.ui.data.PictureUiEvent
import com.example.pixabayapp.features.picture.ui.data.PictureUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val repository: PictureRepository
) : ViewModel() {

    var photoUiState: MutableStateFlow<PictureUiState> =
        MutableStateFlow(PictureUiState())
        private set

    fun reducer(event: PictureUiEvent) {
        when (event) {
            is PictureUiEvent.ClearMessage -> {
                photoUiState.update {
                    it.copy(query = "")
                }
            }

            is PictureUiEvent.UpdateQuery -> {
                photoUiState.update {
                    it.copy(loading = true)
                }
                viewModelScope.launch {
                    when (val response =
                        repository.getPicture(event.query)) {
                        is AppResult.Success -> {
                            photoUiState.update {
                                it.copy(
                                    searchResults = response.data?.hits
                                        ?: emptyList(),
                                    loading = false
                                )
                            }
                        }

                        is AppResult.Error -> {
                            photoUiState.update {
                                it.copy(errorMessage = response.error)
                            }
                        }

                    }
                }
            }

            is PictureUiEvent.OpenShowDetails -> {}
        }

    }
}