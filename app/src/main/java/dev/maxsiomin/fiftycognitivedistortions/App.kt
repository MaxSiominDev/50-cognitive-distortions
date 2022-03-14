package dev.maxsiomin.fiftycognitivedistortions

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    init {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}
