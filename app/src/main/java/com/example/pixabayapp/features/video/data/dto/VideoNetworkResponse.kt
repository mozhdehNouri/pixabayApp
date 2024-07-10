package com.example.pixabayapp.features.video.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoNetworkResponse(
    val hits: List<HitNetworkResponse>,
    val total: Int,
    val totalHits: Int
)

@Serializable
data class HitNetworkResponse(
    val comments: Int,
    val downloads: Int,
    val duration: Int,
    val id: Int,
    val likes: Int,
    val pageURL: String,
    val tags: String,
    val type: String,
    val user: String,
    @SerialName("user_id")
    val userId: Int,
    val userImageURL: String,
    val videos: VideosNetworkResponse,
    val views: Int
)

@Serializable
data class VideosNetworkResponse(
    val large: LargeNetworkResponse,
    val medium: MediumNetworkResponse,
    val small: SmallNetworkResponse,
    val tiny: TinyNetworkResponse
)

@Serializable
data class LargeNetworkResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)

@Serializable
data class MediumNetworkResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)

@Serializable
data class SmallNetworkResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)

@Serializable
data class TinyNetworkResponse(
    val height: Int,
    val size: Int,
    val thumbnail: String,
    val url: String,
    val width: Int
)


