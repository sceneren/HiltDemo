package wiki.scene.hiltdemo

import android.app.Application
import android.content.Context
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.therouter.TheRouter
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context)
        }
    }

    override fun attachBaseContext(base: Context?) {
        TheRouter.isDebug = BuildConfig.DEBUG
        super.attachBaseContext(base)
    }


}