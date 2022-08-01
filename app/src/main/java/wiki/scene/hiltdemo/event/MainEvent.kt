package wiki.scene.hiltdemo.event

import com.kunminx.architecture.domain.event.Event
import wiki.scene.hiltdemo.entity.ArticleInfo

class MainEvent(eventId: Int) : Event<MainEvent.Param, MainEvent.Result>() {
    companion object {
        const val EVENT_GET_DATA = 1
        const val EVENT_FIRST_LOAD = 2
    }

    init {
        this.eventId = eventId
        this.param = Param()
        this.result = Result()
    }

    data class Param(
        var page: Int = 0,
        var pageSize: Int = 40,
        var isFirst: Boolean = true
    )

    data class Result(
        var list: MutableList<ArticleInfo> = mutableListOf(),
        var currentPage: Int = 1,
        var isFirst: Boolean = true,
        var isSuccess: Boolean = false,
        var errorMsg: String = ""
    )
}