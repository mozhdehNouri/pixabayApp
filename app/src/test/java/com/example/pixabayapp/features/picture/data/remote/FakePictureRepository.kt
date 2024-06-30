package com.example.pixabayapp.features.picture.data.remote

import com.example.pixabayapp.core.ApiException
import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.features.picture.ui.data.HitPictureUIResponse
import com.example.pixabayapp.features.picture.ui.data.PictureUIResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test


class FakePictureRepository : PictureRepository {

    private val item = PictureUIResponse(
        listOf<HitPictureUIResponse>(
            HitPictureUIResponse(
                collections = 0,
                comments = 0,
                downloads = 0,
                id = 0,
                imageHeight = 0,
                imageSize = 0,
                imageWidth = 0,
                largeImageURL = "",
                likes = 0,
                pageURL = "",
                previewHeight = 0,
                previewURL = "",
                previewWidth = 0,
                tags = "",
                type = "",
                user = "",
                userId = 0,
                userImageURL = "",
                views = 0,
                webformatHeight = 0,
                webformatURL = "",
                webformatWidth = 0,

                )
        ), 0, 0
    )
    private var isNetworkError = false
    private var isServerError = false
    private var isGeneralError = false
    private var isClientError = false


    override suspend fun getPicture(query: String): AppResult<PictureUIResponse?> {
        return if (isNetworkError) AppResult.Error(ApiException.InternetConnectionException.message)
        else if (isServerError) AppResult.Error(ApiException.ServerResponseException.message)
        else if (isGeneralError) AppResult.Error(ApiException.GeneralException.message)
        else if (isClientError) AppResult.Error(ApiException.ClientRequestException.message)
        else AppResult.Success(item)
    }

    @Test
    fun `is networkError return true`() = runBlocking {
        isNetworkError = true
        val value = getPicture("yellow")
        assertThat(value).isEqualTo(AppResult.Error(ApiException.InternetConnectionException.message))
    }

    @Test
    fun `is serverError return true`() = runBlocking {
        isServerError = true
        val value = getPicture("yellow")
        assertThat(value).isEqualTo(AppResult.Error(ApiException.ServerResponseException.message))
    }

    @Test
    fun `is clientError return true`() = runBlocking {
        isClientError = true
        val value = getPicture("yellow")
        assertThat(value).isEqualTo(AppResult.Error(ApiException.ClientRequestException.message))
    }


}