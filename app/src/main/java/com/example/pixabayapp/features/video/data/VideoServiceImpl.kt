package com.example.pixabayapp.features.video.data

import com.example.pixabayapp.BuildConfig
import com.example.pixabayapp.core.ApiResult
import com.example.pixabayapp.core.Dispatcher
import com.example.pixabayapp.core.PixabayDispatcher
import com.example.pixabayapp.core.apiCallAndCheckResult
import com.example.pixabayapp.core.injection.Utils.VIDEO_BASE_URL
import com.example.pixabayapp.features.video.data.dto.VideoNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class VideoServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
    @Dispatcher(PixabayDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : VideoService {

    override suspend fun getVideo(query: String): ApiResult<VideoNetworkResponse> =
        apiCallAndCheckResult(ioDispatcher, apiCall = {
            httpClient.get(VIDEO_BASE_URL) {
                parameter("key", BuildConfig.KEY)
                parameter("q", query)
            }

        })


}