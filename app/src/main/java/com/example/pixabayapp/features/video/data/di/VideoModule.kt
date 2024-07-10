package com.example.pixabayapp.features.video.data.di

import com.example.pixabayapp.features.video.data.VideoRepository
import com.example.pixabayapp.features.video.data.VideoRepositoryImpl
import com.example.pixabayapp.features.video.data.VideoService
import com.example.pixabayapp.features.video.data.VideoServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface VideoModule {

    @Singleton
    @Binds
    fun bindsVideoService(videoServiceImpl: VideoServiceImpl): VideoService

    @Singleton
    @Binds
    fun bindVideoRepository(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository


}