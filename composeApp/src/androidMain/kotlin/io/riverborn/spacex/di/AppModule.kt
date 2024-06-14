package io.riverborn.spacex.di

import io.riverborn.spacex.SpaceXSDK
import io.riverborn.spacex.cache.AndroidDatabaseDriverFactory
import io.riverborn.spacex.network.SpaceXApi
import io.riverborn.spacex.presentation.RocketLaunchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SpaceXApi> { SpaceXApi() }
    single<SpaceXSDK> {

        SpaceXSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ),
            api = get()
        )
    }

    viewModel { RocketLaunchViewModel(sdk = get()) }
}