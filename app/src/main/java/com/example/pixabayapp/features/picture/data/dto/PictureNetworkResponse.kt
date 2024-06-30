package com.example.pixabayapp.features.picture.data.dto

import com.example.pixabayapp.features.picture.ui.data.HitPictureUIResponse
import com.example.pixabayapp.features.picture.ui.data.PictureUIResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PictureNetworkResponse(
    val hits: List<HitNetworkResponse>,
    val total: Int,
    val totalHits: Int
)

@Serializable
data class HitNetworkResponse(
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    @SerialName("user_id")
    val userId: Int,
    val userImageURL: String,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int
)

fun PictureNetworkResponse.toUiModel() = PictureUIResponse(
    hits = hits.map { it.toUiModel()},
    total,
    totalHits
)

private fun HitNetworkResponse.toUiModel() = HitPictureUIResponse(
    collections,
    comments,
    downloads,
    id,
    imageHeight,
    imageSize,
    imageWidth,
    largeImageURL,
    likes,
    pageURL,
    previewHeight,
    previewURL,
    previewWidth,
    tags,
    type,
    user,
    userId,
    userImageURL,
    views,
    webformatHeight,
    webformatURL,
    webformatWidth,

    )