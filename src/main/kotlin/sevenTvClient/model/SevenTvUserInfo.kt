package org.rieg.sevenTvClient.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SevenTvUserInfo(
    @SerialName("id")
    val id: String,
    @SerialName("platform")
    val platform: String,
    @SerialName("username")
    var username: String,
    @SerialName("display_name")
    var displayName: String,
    @SerialName("emote_set_id")
    var emoteSetId: String,
    @SerialName("emote_set")
    var emoteSet: EmoteSet,
)
