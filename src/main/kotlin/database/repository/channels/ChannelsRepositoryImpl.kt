package database.repository.channels

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.rieg.database.model.TwitchChannel
import org.rieg.database.tables.Channels

class ChannelsRepositoryImpl : ChannelsRepository {
    override fun getChannels(): List<TwitchChannel> = transaction {
        Channels.selectAll().map {
            TwitchChannel(
                twitchId = it[Channels.twitchId],
                twitchUsername = it[Channels.twitchUsername]
            )
        }
    }
}