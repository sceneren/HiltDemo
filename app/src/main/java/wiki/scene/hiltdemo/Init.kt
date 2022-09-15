package wiki.scene.hiltdemo

import android.content.Context
import android.graphics.Color
import com.blankj.utilcode.util.LogUtils
import com.dylanc.loadingstateview.LoadingStateView
import com.github.sceneren.base.state.ErrorViewDelegate
import com.github.sceneren.base.state.LoadingViewDelegate
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.style.IOSStyle
import com.kongzue.dialogx.util.TextInfo
import com.therouter.app.flowtask.lifecycle.FlowTask
import com.therouter.flow.TheRouterFlowTask
import rxhttp.RxHttpPlugins

object Init {

    @JvmStatic
    @FlowTask(taskName = "LoadingStateView")
    fun initLoadingStateView(context: Context) {
        LoadingStateView.setViewDelegatePool {
            register(LoadingViewDelegate(), ErrorViewDelegate())
        }
    }

    @JvmStatic
    @FlowTask(taskName = "RxHttp")
    fun initRxHttp(context: Context) {
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
                LogUtils.e("resultDecoder:$it")
                return@setResultDecoder it
            }
    }


    @JvmStatic
    @FlowTask(taskName = "initDialogX", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
    fun initDialogX(context: Context) {
        DialogX.globalStyle = IOSStyle.style()
        DialogX.titleTextInfo = TextInfo().apply {
            fontSize = 14
            fontColor = Color.parseColor("#ff000000")
        }
        DialogX.okButtonTextInfo = TextInfo().apply {
            fontSize = 14
            fontColor = Color.parseColor("#0088FF")
        }
        DialogX.buttonTextInfo = TextInfo().apply {
            fontSize = 14
            fontColor = Color.parseColor("#666666")
        }
        DialogX.onlyOnePopNotification
    }

}