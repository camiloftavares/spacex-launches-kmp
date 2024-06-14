package io.riverborn.spacex

import io.riverborn.spacex.cache.Database
import io.riverborn.spacex.cache.DatabaseDriverFactory
import io.riverborn.spacex.entity.RocketLaunch
import io.riverborn.spacex.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory, private val api: SpaceXApi) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearAndCreateLaunches(it)
            }
        }
    }
}