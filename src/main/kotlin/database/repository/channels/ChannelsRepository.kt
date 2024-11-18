package database.repository.channels

import org.rieg.database.model.TwitchChannel

interface ChannelsRepository {
    fun getChannels(): List<TwitchChannel>
}