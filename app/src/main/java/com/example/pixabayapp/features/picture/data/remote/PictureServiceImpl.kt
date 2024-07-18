package com.example.pixabayapp.features.picture.data.remote

import com.example.pixabayapp.BuildConfig
import com.example.pixabayapp.core.ApiResult
import com.example.pixabayapp.core.Dispatcher
import com.example.pixabayapp.core.PixabayDispatcher
import com.example.pixabayapp.core.apiCallAndCheckResult
import com.example.pixabayapp.core.Utils.BASE_URL
import com.example.pixabayapp.features.picture.data.dto.PictureNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class PictureServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
    @Dispatcher(PixabayDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : PictureService {
    override suspend fun getPhoto(
        query: String
    ): ApiResult<PictureNetworkResponse> =
        apiCallAndCheckResult(ioDispatcher, apiCall = {
            httpClient.get(BASE_URL) {
                parameter("key", BuildConfig.KEY)
                parameter("q", query)
                parameter("image_type", "photo")
            }
        })
}

