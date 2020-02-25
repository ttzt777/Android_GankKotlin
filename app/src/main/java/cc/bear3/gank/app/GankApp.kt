package cc.bear3.gank.app

import android.app.Application
import timber.log.Timber

/**
 * Description:
 * Author: TT
 * Since: 2020-01-22
 */
class GankApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}