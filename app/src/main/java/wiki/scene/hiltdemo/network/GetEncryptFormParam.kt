package wiki.scene.hiltdemo.network

import com.orhanobut.logger.Logger
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
@Param(methodName = "getEncryptForm")
class GetEncryptFormParam(url: String) : FormParam(url, Method.GET) {

    override fun getRequestBody(): RequestBody {
        Logger.i("bodyParam===>$bodyParam")
        Logger.i("queryParam===>$queryParam")
        Logger.i("headers===>$headers")
        return super.getRequestBody()
    }
}