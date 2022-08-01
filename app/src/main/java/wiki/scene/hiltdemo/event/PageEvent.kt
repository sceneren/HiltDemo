package wiki.scene.hiltdemo.event

import com.kunminx.architecture.domain.event.Event

class PageEvent(eventId: Int) : Event<PageEvent.Param, PageEvent.Result>() {

    class Param
    data class Result(
        val msg: String = "",
        var hasMore: Boolean = true
    )

    companion object {
        const val EVENT_SHOW_LOADING = 1
        const val EVENT_SHOW_CONTENT = 2
        const val EVENT_SHOW_ERROR = 3
        const val EVENT_SHOW_TOAST = 4
        const val EVENT_FINISH_REFRESH = 5
        const val EVENT_FINISH_LOAD_MORE = 6
    }

    init {
        this.eventId = eventId
        param = Param()
        result = Result()
    }
}
