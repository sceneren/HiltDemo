package wiki.scene.hiltdemo

import android.app.Application
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(CustomAndroidLogAdapter())
    }
}