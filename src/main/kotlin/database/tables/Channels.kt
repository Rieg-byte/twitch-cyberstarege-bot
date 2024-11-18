package org.rieg.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Channels: IntIdTable("channels") {
    val twitchId = varchar("twitch_id", 50).uniqueIndex()
    val twitchUsername = varchar("twitch_username", 50).uniqueIndex()
}