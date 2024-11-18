package org.rieg.sevenTvClient.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Emote(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String
)
