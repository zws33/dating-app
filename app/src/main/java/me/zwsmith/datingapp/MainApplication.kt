package me.zwsmith.datingapp

import android.app.Application
import me.zwsmith.datingapp.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(mainModule)
        }
    }
}