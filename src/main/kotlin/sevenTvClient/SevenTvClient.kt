package org.rieg.sevenTvClient

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.rieg.sevenTvClient.model.Emote
import org.rieg.sevenTvClient.model.SevenTvUserInfo

class SevenTvClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "7tv.io"
                path("v3/")
            }
        }
    }
}


suspend fun SevenTvClient.getEmotes(twitchId: String): Map<String, Emote> {
    val response = client.get("users/twitch/$twitchId")
    val emotes = when (response.status.value) {
        in 200..299 -> {
            val sevenTvUserInfo: SevenTvUserInfo = response.body()
            sevenTvUserInfo.emoteSet.emotes.associateBy { it.name }
        }
        else -> mapOf()
    }

    return emotes
}