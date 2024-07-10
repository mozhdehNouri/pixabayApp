package com.example.pixabayapp.features.video.data

import com.example.pixabayapp.core.ApiResult
import com.example.pixabayapp.features.video.data.dto.VideoNetworkResponse

interface VideoService {
    suspend fun getVideo(query: String): ApiResult<VideoNetworkResponse>
}