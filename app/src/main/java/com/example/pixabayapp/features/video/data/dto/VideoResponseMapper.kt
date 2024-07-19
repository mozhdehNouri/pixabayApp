package com.example.pixabayapp.features.video.data.dto

import com.example.pixabayapp.features.video.ui.data.HitVideoUiResponse
import com.example.pixabayapp.features.video.ui.data.LargeUiResponse
import com.example.pixabayapp.features.video.ui.data.MediumUiResponse
import com.example.pixabayapp.features.video.ui.data.SmallUiResponse
import com.example.pixabayapp.features.video.ui.data.TinyUiResponse
import com.example.pixabayapp.features.video.ui.data.VideoUiResponse
import com.example.pixabayapp.features.video.ui.data.VideosUiResponse


fun VideoNetworkResponse.toUiModel() =
    VideoUiResponse(hits.map { it.toUiModel() }, total, totalHits)

private fun HitNetworkResponse.toUiModel() = HitVideoUiResponse(
    comments,
    downloads,
    duration,
    id,
    likes,
    pageURL,
    tags,
    type,
    user,
    userId,
    userImageURL,
    videos.toUiModel(),
    views
)

private fun VideosNetworkResponse.toUiModel() = VideosUiResponse(
    large.toUiModel(),
    medium.toUiModel(),
    small.toUiModel(),
    tiny.toUiModel()
)

private fun LargeNetworkResponse.toUiModel() = LargeUiResponse(
    height,
    size,
    thumbnail,
    url,
    width
)

private fun MediumNetworkResponse.toUiModel() = MediumUiResponse(
    height,
    size,
    thumbnail,
    url,
    width
)

private fun SmallNetworkResponse.toUiModel() = SmallUiResponse(
    height,
    size,
    thumbnail,
    url,
    width
)

private fun TinyNetworkResponse.toUiModel() = TinyUiResponse(
    height,
    size,
    thumbnail,
    url,
    width
)
