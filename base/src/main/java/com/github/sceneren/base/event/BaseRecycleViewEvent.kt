package com.github.sceneren.base.event

open class BaseRecycleViewEvent<P, R>(eventId: Int) : BaseEvent<P, R>(eventId) {
    companion object {
        const val EVENT_FINISH_REFRESH_SUCCESS = 107
        const val EVENT_FINISH_REFRESH_FAIL = 108
        const val EVENT_FINISH_LOAD_MORE_COMPLETE = 109
        const val EVENT_FINISH_LOAD_MORE_END = 110
        const val EVENT_FINISH_LOAD_MORE_FAIL = 111
    }
}