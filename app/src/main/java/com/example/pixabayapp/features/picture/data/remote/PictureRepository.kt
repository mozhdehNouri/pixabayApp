package com.example.pixabayapp.features.picture.data.remote

import com.example.pixabayapp.core.AppResult
import com.example.pixabayapp.features.picture.ui.data.PictureUIResponse

interface PictureRepository {

    suspend fun getPicture(
        query: String
    ): AppResult<PictureUIResponse?>

}