package org.rieg.database.repository.sevenTvEmotes

import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.statements.UpsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert
import org.rieg.database.model.SevenTvEmote
import org.rieg.database.tables.SevenTvEmotes

class SevenTvEmotesRepositoryImpl : SevenTvEmotesRepository {
    override fun upsertEmote(emote: SevenTvEmote): UpsertStatement<Long> = transaction {
        SevenTvEmotes.upsert(
            SevenTvEmotes.emoteId,
            SevenTvEmotes.twitchId,
            onUpdate = {
                it[SevenTvEmotes.emoteCount] = SevenTvEmotes.emoteCount + emote.emoteCount
            }
        ) {
            it[emoteName] = emote.emoteName
            it[emoteId] = emote.emoteId
            it[emoteCount] = emote.emoteCount
            it[twitchId] = emote.twitchId
        }
    }
}