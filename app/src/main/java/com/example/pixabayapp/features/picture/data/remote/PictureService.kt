package com.example.pixabayapp.features.picture.data.remote

import com.example.pixabayapp.core.ApiResult
import com.example.pixabayapp.features.picture.data.dto.PictureNetworkResponse


interface PictureService {

    suspend fun getPhoto(
        query: String
    ): ApiResult<PictureNetworkResponse>

}