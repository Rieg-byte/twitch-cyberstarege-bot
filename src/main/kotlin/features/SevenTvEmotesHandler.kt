package org.rieg.features

import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.rieg.database.model.SevenTvEmote
import org.rieg.database.repository.sevenTvEmotes.SevenTvEmotesRepository
import org.rieg.sevenTvClient.model.Emote

private val logger = KotlinLogging.logger {}

class SevenTvEmotesHandler(
    eventHandler: SimpleEventHandler,
    private val twitchChannelsToEmotes: Map<String, Map<String, Emote>>,
    private val sevenTvEmotesRepository: SevenTvEmotesRepository
    ) {

    init {
        eventHandler.onEvent(ChannelMessageEvent::class.java, this::onHandleEmotes)
    }

    private fun onHandleEmotes(event: ChannelMessageEvent) {
        logger.info {
            "[${event.channel.name}] ${event.user.name}: ${event.message}"
        }
        val channelName = event.channel.name ?: ""
        val emotes = twitchChannelsToEmotes.getOrElse(channelName) { emptyMap() }
        if (emotes.isNotEmpty()) {
            val emotesToUpsert = mutableMapOf<String, Int>()
            val words = event.message.split(" ")
            for (word in words) {
                val emote = emotes[word]
                if (emote != null) {
                    emotesToUpsert[emote.name] = emotesToUpsert.getOrDefault(emote.name, 0) + 1
                }
            }
            emotesToUpsert.forEach { (emoteName, emoteCount) ->
                emotes[emoteName]?.let {
                    val sevenTvEmote = SevenTvEmote(
                        emoteName = it.name,
                        emoteId = it.id,
                        emoteCount = emoteCount,
                        twitchId = event.channel.id
                    )
                    logger.info {
                        "Added sevenTvEmote: emoteName - ${sevenTvEmote.emoteName} count - ${sevenTvEmote.emoteCount}"
                    }
                    sevenTvEmotesRepository.upsertEmote(sevenTvEmote)
                }
            }
        }
    }
}