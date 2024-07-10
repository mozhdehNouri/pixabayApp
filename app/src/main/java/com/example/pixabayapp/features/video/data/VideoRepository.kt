package com.example.pixabayapp.features.video.data

import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.features.video.ui.data.VideoUiResponse

interface VideoRepository {
    suspend fun getVideo(query: String): AppResult<VideoUiResponse?>
}