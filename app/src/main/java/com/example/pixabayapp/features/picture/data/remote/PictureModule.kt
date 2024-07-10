package com.example.pixabayapp.features.picture.data.remote

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PictureModule {

    @Binds
    abstract fun bindApiService(
        pictureService: PictureServiceImpl
    ): PictureService


    @Binds
    abstract fun bindPhotoRepository(impl: PictureRepositoryImpl):
            PictureRepository


}