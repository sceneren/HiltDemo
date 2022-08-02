package wiki.scene.hiltdemo.requester

import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.domain.dispatch.MviDispatcher
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse
import wiki.scene.hiltdemo.entity.ArticleInfo
import wiki.scene.hiltdemo.event.BaseEvent
import wiki.scene.hiltdemo.event.BaseRecycleViewEvent
import wiki.scene.hiltdemo.event.MainEvent
import wiki.scene.hiltdemo.network.BasePageListDataResponse
import wiki.scene.hiltdemo.network.msg

class MainListRequester : MviDispatcher<MainEvent>() {
    override fun onHandle(event: MainEvent) {
        super.onHandle(event)
        when (event.eventId) {
            MainEvent.EVENT_GET_DATA -> {
                viewModelScope.launch {
                    RxHttp.get("article/list/%d/json", event.param.page)
                        .add("page_size", event.param.pageSize)
                        .toFlowResponse<BasePageListDataResponse<ArticleInfo>>()
                        .onStart {
                            if (event.param.isFirst) {
                                sendResult(MainEvent(BaseEvent.EVENT_SHOW_LOADING_PAGE))
                            }
                        }
                        .catch {
                            if (event.result.isFirst) {
                                sendResult(MainEvent(BaseEvent.EVENT_SHOW_ERROR_PAGE))
                            } else {
                                if (event.param.page == 1) {
                                    sendResult(MainEvent(BaseEvent.EVENT_SHOW_ERROR_PAGE))
                                } else {
                                    sendResult(MainEvent(BaseRecycleViewEvent.EVENT_FINISH_LOAD_MORE_FAIL))
                                }
                            }
                            sendResult(MainEvent(BaseEvent.EVENT_SHOW_TOAST).apply {
                                result.errorMsg = it.msg
                            })
                        }
                        .collect {
                            Logger.e(it.toString())
                            event.result.list = it.datas
                            event.result.currentPage = event.param.page
                            sendResult(event)

                            if (event.param.isFirst) {
                                sendResult(MainEvent(BaseEvent.EVENT_SHOW_CONTENT_PAGE))
                            } else {
                                if (event.result.currentPage == 1) {
                                    sendResult(MainEvent(BaseRecycleViewEvent.EVENT_FINISH_REFRESH_SUCCESS))
                                } else {
                                    if (it.pageCount > it.curPage) {
                                        sendResult(MainEvent(BaseRecycleViewEvent.EVENT_FINISH_LOAD_MORE_COMPLETE))
                                    } else {
                                        sendResult(MainEvent(BaseRecycleViewEvent.EVENT_FINISH_LOAD_MORE_END))
                                    }

                                }
                            }

                        }
                }
            }
        }
    }
}