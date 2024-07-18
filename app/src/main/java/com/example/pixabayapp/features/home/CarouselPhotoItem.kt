package com.example.pixabayapp.features.home

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.example.pixabayapp.R

@Immutable
data class CarouselPhotoItem(
    val id: Int,
    @DrawableRes val imageRes: Int,
//    @DrawableRes val imageDesc: Int
)

@Immutable
data class CarouselVideoItem(
    val id:Int,
    val videoLink:String
)

val photoItem =
    listOf<CarouselPhotoItem>(CarouselPhotoItem(0, R.drawable.pic_sample_1),
        CarouselPhotoItem(1, R.drawable.pic_sample_2),
        CarouselPhotoItem(2, R.drawable.pic_sample_3),
        CarouselPhotoItem(3, R.drawable.pic_sample_4),
        CarouselPhotoItem(4, R.drawable.pic_sample_5),
        CarouselPhotoItem(5, R.drawable.pic_sample_6),
        CarouselPhotoItem(6, R.drawable.pic_sample_7))
val videoItem = listOf<CarouselVideoItem>(
    CarouselVideoItem(3,"")
)
val musicItem = listOf<CarouselPhotoItem>()