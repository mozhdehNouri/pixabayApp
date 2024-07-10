package com.example.pixabayapp.features.video.data

import android.content.Context
import com.example.pixabayapp.core.ApiException
import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.core.networkconnection.isInternetAvailable
import com.example.pixabayapp.core.toAppResult
import com.example.pixabayapp.features.video.data.dto.toUiModel
import com.example.pixabayapp.features.video.ui.data.VideoUiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val service: VideoService,
    @ApplicationContext private val context: Context
) : VideoRepository {

    override suspend fun getVideo(query: String): AppResult<VideoUiResponse?> {
        if (!isInternetAvailable(context)) {
            return AppResult.Error(ApiException.InternetConnectionException.message)
        }
        return when (val response =
            service.getVideo(query).toAppResult()) {
            is AppResult.Success -> {
                AppResult.Success(response.data?.toUiModel())
            }

            is AppResult.Error -> {
                AppResult.Error(response.error)
            }
        }
    }
}