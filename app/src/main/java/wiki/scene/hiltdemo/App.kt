package wiki.scene.hiltdemo

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import rxhttp.RxHttpPlugins
import wiki.scene.hiltdemo.base.LoadingViewDelegate
import java.util.concurrent.TimeUnit


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

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}