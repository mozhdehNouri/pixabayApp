package com.example.pixabayapp.features.video.ui.data


data class VideoUiResponse(
    val hits: List<HitUiResponse>,
    val total: Int,
    val totalHits: Int
)

data class HitUiResponse(
    val comments: Int,
    val downloads: Int,
    val duration: Int,
    val id: Int,
    val likes: Int,
    val pageURL: String,
    val tags: String,
    val type: String,
    val user: String,
    val userId: Int,
    val userImageURL: String,
    val videos: VideosUiResponse,
    val views: Int
)

data class VideosUiResponse(
    val large: LargeUiResponse,
    val medium: MediumUiResponse,
    val small: SmallUiResponse,
    val tiny: TinyUiResponse
)

data class LargeUiResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)

data class MediumUiResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)

data class SmallUiResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)

data class TinyUiResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)