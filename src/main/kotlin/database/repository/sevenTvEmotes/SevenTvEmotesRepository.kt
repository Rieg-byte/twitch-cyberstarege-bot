package org.rieg.database.repository.sevenTvEmotes

import org.jetbrains.exposed.sql.statements.UpsertStatement
import org.rieg.database.model.SevenTvEmote

interface SevenTvEmotesRepository {
    fun upsertEmote(emote: SevenTvEmote): UpsertStatement<Long>
}