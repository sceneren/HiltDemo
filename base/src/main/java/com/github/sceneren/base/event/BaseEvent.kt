package com.github.sceneren.base.event

import com.kunminx.architecture.domain.event.Event

open class BaseEvent<P, R>(eventId: Int) : Event<P, R>() {
    companion object {
        const val EVENT_SHOW_LOADING_PAGE = 101
        const val EVENT_SHOW_ERROR_PAGE = 102
        const val EVENT_SHOW_CONTENT_PAGE = 103
        const val EVENT_SHOW_TOAST = 104
        const val EVENT_SHOW_LOADING_DIALOG = 105
        const val EVENT_HIDE_LOADING_DIALOG = 106
    }

    init {
        this.eventId = eventId
    }
}