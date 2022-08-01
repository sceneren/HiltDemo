package wiki.scene.hiltdemo.event

import com.kunminx.architecture.domain.event.Event
import wiki.scene.hiltdemo.BookInfo

class MainEvent(eventId: Int) : Event<MainEvent.Param, MainEvent.Result>() {
    companion object {
        const val EVENT_GET_DATA = 1
    }

    init {
        this.eventId = eventId
        this.param = Param()
        this.result = Result()
    }

    data class Param(
        var page: Int = 0,
        var pageSize: Int = 20,
        var isFirst: Boolean = true
    )

    data class Result(
        var list: MutableList<BookInfo> = mutableListOf(),
        var currentPage: Int = 1,
        var isFirst: Boolean = true
    )
}