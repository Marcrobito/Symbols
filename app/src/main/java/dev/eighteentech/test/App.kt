package dev.eighteentech.test

import android.app.Application
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@InternalCoroutinesApi
val modules = listOf(appModule, repositoryModule, networkModule)

@InternalCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(modules)
        }
    }
}