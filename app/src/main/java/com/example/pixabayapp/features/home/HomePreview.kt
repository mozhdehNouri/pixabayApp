package com.example.pixabayapp.features.home

import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pixabayapp.features.PixabayDarkPreview


@PixabayDarkPreview
@Composable
fun PhotoBannerPreview(modifier: Modifier = Modifier) {
    PhotoBanner(photoList = emptyList())
}

@PixabayDarkPreview
@Composable
fun VideoBannerPreview(modifier: Modifier = Modifier) {
    VideoBanner(videoList =  emptyList())
}