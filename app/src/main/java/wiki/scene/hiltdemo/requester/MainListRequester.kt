package wiki.scene.hiltdemo.requester

import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.domain.dispatch.MviDispatcher
import com.orhanobut.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse
import rxhttp.wrapper.param.toResponse
import wiki.scene.hiltdemo.entity.ArticleInfo
import wiki.scene.hiltdemo.event.MainEvent
import wiki.scene.hiltdemo.network.BasePageListDataResponse
import wiki.scene.hiltdemo.network.BaseResponse

class MainListRequester : MviDispatcher<MainEvent>() {
    override fun onHandle(event: MainEvent) {
        super.onHandle(event)
        when (event.eventId) {
            MainEvent.EVENT_GET_DATA -> {
                viewModelScope.launch {
                    RxHttp.get("article/list/%d/json",event.param.page)
                        .add("page_size", event.param.pageSize)
                        .toFlowResponse<BasePageListDataResponse<ArticleInfo>>()
                        .onStart {
                            Logger.e("onStart")
                        }
                        .catch {
                            event.result.currentPage = event.param.page
                            event.result.isFirst = event.param.isFirst
                            event.result.isSuccess = false
                            event.result.errorMsg = it.message ?: ""
                            sendResult(event)
                        }
                        .collect {
                            Logger.e(it.toString())
                            event.result.currentPage = event.param.page
                            event.result.isFirst = event.param.isFirst
                            event.result.isSuccess = true
                            event.result.list = it.datas
                            sendResult(event)
                        }
                }
            }
        }
    }
}