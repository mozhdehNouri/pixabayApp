package com.example.pixabayapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.features.picture.data.remote.PictureRepository
import com.example.pixabayapp.features.picture.ui.data.PictureUIResponse
import com.example.pixabayapp.features.video.data.VideoRepository
import com.example.pixabayapp.features.video.ui.data.VideoUiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photoRepository: PictureRepository,
    private val videoRepository: VideoRepository
) : ViewModel() {

    var uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        private set

    init {
        getHomeBannerData()
    }

    fun getHomeBannerData() = viewModelScope.launch {
        uiState.update { HomeUiState.Loading }

        val deferredResults = listOf(
            async { photoRepository.getPicture("sunset") },
            async { photoRepository.getPicture("animal") },
            async { videoRepository.getVideo("yellow") }
        )
        val results = awaitAll(*deferredResults.toTypedArray())
        when {
            results.any { it is AppResult.Error } -> {
                val errorMessage = results.find { it is AppResult.Error }?.let {
                    (it as AppResult.Error).error
                } ?: "An error occurred"
                uiState.update { HomeUiState.Error(errorMessage) }
            }

            results.isEmpty() -> {
                uiState.update { HomeUiState.Error("Empty result") }
            }

            else -> {
                val explorePic = results.filterIsInstance<PictureUIResponse>()[0]
                    .hits.map { it.userImageURL }.slice(0..5)

                val animalPic =
                    results.filterIsInstance<PictureUIResponse>()[1].hits.map { it.previewURL }


                val video = results.filterIsInstance<VideoUiResponse>()[1]
                    .hits.map { it.videos.small.thumbnail }.slice(0..5)
                uiState.update {
                    HomeUiState.Success(
                        explorePic = explorePic,
                        animalPic = animalPic,
                        video = video
                    )
                }
            }
        }
    }

}

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(
        val explorePic: List<String>,
        val animalPic: List<String>,
        val video: List<String>
    ) : HomeUiState()

    data class Error(val message: String?) : HomeUiState()
}
