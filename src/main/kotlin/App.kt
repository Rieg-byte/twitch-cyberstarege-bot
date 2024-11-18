package org.rieg

import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClientBuilder
import database.repository.channels.ChannelsRepository
import database.repository.channels.ChannelsRepositoryImpl
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.runBlocking
import org.rieg.database.connectDatabase
import org.rieg.database.repository.sevenTvEmotes.SevenTvEmotesRepository
import org.rieg.database.repository.sevenTvEmotes.SevenTvEmotesRepositoryImpl
import org.rieg.features.SevenTvEmotesHandler
import org.rieg.sevenTvClient.SevenTvClient
import org.rieg.sevenTvClient.getEmotes
import org.rieg.sevenTvClient.model.Emote

private val logger = KotlinLogging.logger {}

fun main() {
    connectDatabase()
    val channelsRepository: ChannelsRepository = ChannelsRepositoryImpl()
    val sevenTvEmotesRepository: SevenTvEmotesRepository = SevenTvEmotesRepositoryImpl()
    val twitchClient = TwitchClientBuilder.builder()
        .withEnableHelix(true)
        .withClientId(System.getenv("TWITCH_CLIENT_ID"))
        .withClientSecret(System.getenv("TWITCH_CLIENT_SECRET"))
        .withDefaultEventHandler(SimpleEventHandler::class.java)
        .withEnableChat(true)
        .build()

    val sevenTvClient = SevenTvClient()
    val twitchChannels = channelsRepository.getChannels()
    val twitchChannelsToEmotes = mutableMapOf<String, Map<String, Emote>>()
    for (twitchChannel in twitchChannels) {
        val twitchUsername = twitchChannel.twitchUsername.lowercase()
        twitchClient.chat.joinChannel(twitchUsername)
        runBlocking {
            twitchChannelsToEmotes[twitchUsername] = sevenTvClient.getEmotes(twitchChannel.twitchId)
        }
        logger.info {
            "$twitchUsername - ${twitchChannelsToEmotes[twitchUsername]}"
        }
    }

    val eventManager = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)
    SevenTvEmotesHandler(eventManager, twitchChannelsToEmotes, sevenTvEmotesRepository)
}

