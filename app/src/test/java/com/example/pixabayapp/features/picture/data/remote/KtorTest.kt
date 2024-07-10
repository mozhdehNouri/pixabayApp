package com.example.pixabayapp.features.picture.data.remote

import com.example.pixabayapp.features.picture.data.dto.HitNetworkResponse
import com.example.pixabayapp.features.picture.data.dto.PictureNetworkResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.json.JsonPlugin.Plugin.install
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorTest {

    private val item = PictureNetworkResponse(
        listOf(
            HitNetworkResponse(
                collections = 0,
                comments = 0,
                downloads = 0,
                id = 0,
                imageHeight = 0,
                imageSize = 0,
                imageWidth = 0,
                largeImageURL = "",
                likes = 0,
                pageURL = "",
                previewHeight = 0,
                previewURL = "",
                previewWidth = 0,
                tags = "",
                type = "",
                user = "",
                userId = 0,
                userImageURL = "",
                views = 0,
                webformatHeight = 0,
                webformatURL = "",
                webformatWidth = 0,
            )
        ), total = 0, totalHits = 0
    )

    private fun ContentType.Application.module() {
        install(ContentNegotiation) {
            json(Json { prettyPrint = true })
        }

        routing {
            get("/") {
                if (call.request.queryParameters["image_type"] == "query" &&
                    call.request.queryParameters["q"] == "photo"
                ) {
                    call.respond(item)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }

    @Test
    fun `test ktor`() = testApplication {
        application { module() }

        val response = client.get("/") {
            parameter("image_type", "query")
            parameter("q", "photo")
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(item, response.body<PictureNetworkResponse>())
    }
}
