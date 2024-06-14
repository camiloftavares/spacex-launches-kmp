package io.riverborn.spacex

import io.riverborn.spacex.cache.IOSDatabaseDriverFactory
import io.riverborn.spacex.entity.RocketLaunch
import io.riverborn.spacex.network.SpaceXApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper : KoinComponent {
    private val sdk: SpaceXSDK by inject<SpaceXSDK>()

    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

fun initKoin() {
    startKoin {
        modules(module {
            single<SpaceXApi> { SpaceXApi() }
            single {
                SpaceXSDK(
                    databaseDriverFactory = IOSDatabaseDriverFactory(),
                    api = get()
                )
            }
        })
    }
}