package wiki.scene.hiltdemo.network

import com.blankj.utilcode.util.LogUtils
import okhttp3.RequestBody
import rxhttp.wrapper.annotation.Param
import rxhttp.wrapper.param.FormParam
import rxhttp.wrapper.param.Method

/**
 *
 * @Description:    此处可以做一些请求加密的东西
 * @Author:         scene
 * @CreateDate:     2022/8/1 15:13
 * @UpdateUser:
 * @UpdateDate:     2022/8/1 15:13
 * @UpdateRemark:
 * @Version:        1.0.0
 */
@Param(methodName = "postEncryptForm")
class PostEncryptFormParam(url: String) : FormParam(url, Method.POST) {

    override fun getRequestBody(): RequestBody {
        LogUtils.e("bodyParam===>$bodyParam")
        LogUtils.e("queryParam===>$queryParam")
        LogUtils.e("headers===>$headers")
        addHeader("bodyParam", bodyParam.toString())
        return super.getRequestBody()
    }
}