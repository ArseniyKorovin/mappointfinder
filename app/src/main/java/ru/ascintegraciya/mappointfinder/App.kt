package ru.ascintegraciya.mappointfinder

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {
    companion object {
        private const val KEY_MAPKIT_API= "a2a37a0f-8505-4e04-8e39-3fb17decaced"
    }

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey(KEY_MAPKIT_API)
    }
}