package wiki.scene.hiltdemo.ui.home.event

import com.github.sceneren.base.event.BaseEvent
import wiki.scene.hiltdemo.entity.ProjectTreeInfo

class HomeEvent(eventId: Int) : BaseEvent<Any, HomeEvent.Result>(eventId) {
    companion object {
        const val EVENT_PROJECT_TREE = 1
    }

    init {
        this.param = Any()
        this.result = Result()
    }

    data class Result(
        var list: MutableList<ProjectTreeInfo> = mutableListOf(),
        var isSuccess: Boolean = false,
        var errorMsg: String = ""
    )
}

