package org.rieg.database.model

import kotlinx.serialization.Serializable

@Serializable
data class TwitchChannel(
    val twitchId: String,
    val twitchUsername: String,
)
