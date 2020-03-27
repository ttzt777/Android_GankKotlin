package cc.bear3.osbear.app

import android.app.Application
import android.content.Context
import cc.bear3.osbear.BuildConfig
import timber.log.Timber

/**
 * Description:
 * Author: TT
 * Since: 2020-01-22
 */
class BearApp : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}