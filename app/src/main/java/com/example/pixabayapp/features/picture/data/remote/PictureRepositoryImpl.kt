package com.example.pixabayapp.features.picture.data.remote

import android.content.Context
import com.example.pixabayapp.core.ApiException
import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.core.networkconnection.isInternetAvailable
import com.example.pixabayapp.core.toAppResult
import com.example.pixabayapp.features.picture.data.dto.toUiModel
import com.example.pixabayapp.features.picture.ui.data.PictureUIResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val service: PictureService,
    @ApplicationContext private val context: Context
) : PictureRepository {

    override suspend fun getPicture(
        query: String
    ): AppResult<PictureUIResponse?> {
        if (!isInternetAvailable(context)) return AppResult.Error(ApiException.InternetConnectionException.message)

        return when (val response = service.getPhoto(query).toAppResult()) {
            is AppResult.Success -> {
                AppResult.Success(response.data?.toUiModel())
            }

            is AppResult.Error -> {
                AppResult.Error(response.error)
            }
        }
    }
}