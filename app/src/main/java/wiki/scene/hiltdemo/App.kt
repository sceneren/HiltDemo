package wiki.scene.hiltdemo

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp
import wiki.scene.hiltdemo.base.LoadingViewDelegate


@HiltAndroidApp
class App : Application() {
    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(CustomAndroidLogAdapter())
        LoadingStateView.setViewDelegatePool {
            register(LoadingViewDelegate())
        }
    }
}