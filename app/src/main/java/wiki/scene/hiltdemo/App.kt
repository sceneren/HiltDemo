package wiki.scene.hiltdemo

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import wiki.scene.hiltdemo.base.LoadingViewDelegate


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(CustomAndroidLogAdapter())
        LoadingStateView.setViewDelegatePool{
            register(LoadingViewDelegate())
        }
    }
}