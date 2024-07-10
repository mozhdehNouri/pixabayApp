package com.example.pixabayapp.core

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: PixabayDispatcher)

enum class PixabayDispatcher {
    Default,
    IO
}
