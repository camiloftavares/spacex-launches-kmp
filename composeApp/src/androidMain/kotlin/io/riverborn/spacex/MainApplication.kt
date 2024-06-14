package io.riverborn.spacex

import android.app.Application
import io.riverborn.spacex.di.appModule
import io.riverborn.spacex.network.SpaceXApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}