package wiki.scene.hiltdemo.ui.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.github.sceneren.base.event.BaseEvent
import com.kunminx.architecture.domain.dispatch.MviDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse
import wiki.scene.hiltdemo.entity.ProjectTreeInfo
import wiki.scene.hiltdemo.ui.home.event.HomeEvent

class HomeViewModel : MviDispatcher<HomeEvent>() {

    override fun onHandle(event: HomeEvent) {
        super.onHandle(event)
        when (event.eventId) {
            HomeEvent.EVENT_PROJECT_TREE -> {
                viewModelScope.launch {
                    RxHttp.get("/project/tree/json").toFlowResponse<MutableList<ProjectTreeInfo>>()
                        .onStart {
                            sendResult(HomeEvent(BaseEvent.EVENT_SHOW_LOADING_PAGE))
                        }.catch {
                            sendResult(HomeEvent(BaseEvent.EVENT_SHOW_ERROR_PAGE))
                        }.collect {
                            sendResult(HomeEvent(BaseEvent.EVENT_SHOW_CONTENT_PAGE))
                            event.result.list = it
                            sendResult(event)
                        }
                }
            }
        }
    }
}