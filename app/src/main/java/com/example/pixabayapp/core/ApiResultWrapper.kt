package com.example.pixabayapp.core

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


suspend inline fun <reified T> apiCallAndCheckResult(
    dispatcher: CoroutineDispatcher,
    crossinline apiCall: suspend () -> HttpResponse
): ApiResult<T> {

    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke()
            checkApiResult(response)
        } catch (e: Exception) {
            ApiResult.Error(ApiException.GeneralException)
        }
    }
}

suspend inline fun <reified T> checkApiResult(
    response: HttpResponse,
): ApiResult<T> {
    return if (response.status == HttpStatusCode.OK) {
        ApiResult.Success(response.body<T>())
    } else {
        when (response.status.value) {
            in 400..499 -> ApiResult.Error(ApiException.ClientRequestException)
            in 500..599 -> ApiResult.Error(ApiException.ServerResponseException)
            else -> ApiResult.Error(ApiException.GeneralException)
        }
    }
}

