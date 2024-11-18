package org.rieg.sevenTvClient.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmoteSet (
    @SerialName("emotes")
    val emotes: List<Emote>
)