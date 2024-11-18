package org.rieg.database.model

import kotlinx.serialization.Serializable

@Serializable
data class SevenTvEmote(
    val emoteName: String,
    val emoteId: String,
    val emoteCount: Int,
    val twitchId: String
)
