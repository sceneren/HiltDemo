package wiki.scene.hiltdemo.event

import com.github.sceneren.base.event.BaseRecycleViewEvent
import wiki.scene.hiltdemo.entity.ArticleInfo

class MainEvent(eventId: Int) : BaseRecycleViewEvent<MainEvent.Param, MainEvent.Result>(eventId) {
    companion object {
        const val EVENT_GET_DATA = 1
    }

    init {
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
        var totalPage: Int = 0,
        var isFirst: Boolean = true,
        var isSuccess: Boolean = false,
        var errorMsg: String = ""
    )
}