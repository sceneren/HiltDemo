package wiki.scene.hiltdemo.message

import com.kunminx.architecture.domain.dispatch.MviDispatcher
import wiki.scene.hiltdemo.event.PageEvent

class PageMessenger : MviDispatcher<PageEvent>() {

    override fun onHandle(event: PageEvent) {
        sendResult(event)
    }
}