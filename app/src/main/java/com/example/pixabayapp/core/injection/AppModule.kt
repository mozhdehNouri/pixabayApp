package com.example.pixabayapp.core.injection

import com.example.pixabayapp.core.networkconnection.ConnectivityManagerNetworkMonitor
import com.example.pixabayapp.core.networkconnection.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
    fun bindNetworkConnection(connectionImpl: ConnectivityManagerNetworkMonitor):NetworkMonitor
}