package org.rieg.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object SevenTvEmotes: IntIdTable("seven_tv_emotes") {
    val emoteName = text("emote_name")
    val emoteId = varchar("emote_id", 50).uniqueIndex()
    val emoteCount = integer("emote_count").default(0)
    val twitchId = reference("twitch_id", Channels.twitchId, onDelete = ReferenceOption.CASCADE).uniqueIndex()
}