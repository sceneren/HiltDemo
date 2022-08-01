package wiki.scene.hiltdemo.requester

import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.domain.dispatch.MviDispatcher
import com.orhanobut.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wiki.scene.hiltdemo.BookInfo
import wiki.scene.hiltdemo.event.MainEvent

class MainListRequester : MviDispatcher<MainEvent>() {
    override fun onHandle(event: MainEvent) {
        super.onHandle(event)
        when (event.eventId) {
            MainEvent.EVENT_GET_DATA -> {
                viewModelScope.launch {
                    val data = mutableListOf<BookInfo>()
                    val start = (event.param.pageSize * (event.param.page - 1) + 1)
                    val end = (event.param.pageSize * event.param.page)
                    for (i in start..end) {
                        data.add(BookInfo("Android $i"))
                    }
                    delay(2000)
                    event.result.list = data
                    event.result.currentPage = event.param.page
                    event.result.isFirst = event.param.isFirst
                    sendResult(event)
                }
            }
        }
    }
}