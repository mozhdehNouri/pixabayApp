package com.example.pixabayapp.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineModule {
    @Provides
    @Dispatcher(PixabayDispatcher.IO)
    fun provideIODispatcher(): CoroutineDispatcher = IO

    @Dispatcher(PixabayDispatcher.Default)
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Default
}