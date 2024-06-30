package com.example.pixabayapp.core

sealed class ApiResult<out T>(
    val data: T? = null,
    val error: ApiException? = null
) {
    class Success<T>(data: T) : ApiResult<T>(data = data)
    class Error<T>(error: ApiException) : ApiResult<T>(error = error)
}

sealed class ApiException(val message: String) {
    data object InternetConnectionException :
        ApiException(message = "No Internet Connection")

    data object ClientRequestException :
        ApiException(message = "Problem From client side")

    data object ServerResponseException :
        ApiException(message = "Server side exception")

    data object GeneralException :
        ApiException(message = "Something wrong please try again")
}

fun <T> ApiResult<T>.toAppResult() = when (this) {
    is ApiResult.Success -> AppResult.Success(data)
    is ApiResult.Error -> AppResult.Error(error?.message)
}