package wiki.scene.hiltdemo

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.github.sceneren.base.state.LoadingViewDelegate
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp
import rxhttp.RxHttpPlugins


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

        RxHttpPlugins.init(null)      //自定义OkHttpClient对象
            .setDebug(BuildConfig.DEBUG)
            .setOnParamAssembly {
                //可以在此处添加全局参数和全局请求头
                val method = it.method
                it.addHeader("requestToken", "token")
                if (method.isGet) {
                    it.add("requestType", "get")
                } else {
                    it.addHeader("username", "sceneren")
                    it.addHeader("password", "123456")
                }
                return@setOnParamAssembly it
            }
            .setResultDecoder {
                Logger.e("resultDecoder:$it")
                return@setResultDecoder it
            }
    }
}