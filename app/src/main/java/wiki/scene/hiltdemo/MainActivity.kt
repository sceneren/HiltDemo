package wiki.scene.hiltdemo

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.dylanc.loadingstateview.ViewType
import com.dylanc.mmkv.*
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.DataRepository.uid
import wiki.scene.hiltdemo.adapter.MainAdapter
import wiki.scene.hiltdemo.base.BaseBindingActivity
import wiki.scene.hiltdemo.base.LoadingViewDelegate
import wiki.scene.hiltdemo.databinding.ActivityMainBinding
import wiki.scene.hiltdemo.event.MainEvent
import wiki.scene.hiltdemo.event.PageEvent
import wiki.scene.hiltdemo.hilt.factory.MainAdapterFactory
import wiki.scene.hiltdemo.message.PageMessenger
import wiki.scene.hiltdemo.requester.MainListRequester
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>(), OnRefreshListener,
    OnLoadMoreListener {
    @Inject
    lateinit var mainAdapterFactory: MainAdapterFactory

    private lateinit var mMessenger: PageMessenger
    private lateinit var mRequester: MainListRequester
    private lateinit var adapter: MainAdapter

    override val contentView: View
        get() = binding.refreshLayout

    private var currentPage = 1

    override fun onBackPressed() {
        exitProcess(0)
    }

    override fun onInitViewModel() {
        mMessenger = getActivityScopeViewModel(PageMessenger::class.java)
        mRequester = getActivityScopeViewModel(MainListRequester::class.java)
    }

    override fun onInitView() {
        binding.refreshLayout.setOnRefreshListener(this)
        adapter = mainAdapterFactory.createMainAdapter(1)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener { _, _, position ->
            uid = position.toString()
            if (position == 0) {
                DataRepository.data = "DataRepository"
                UserRepository.data = "UserRepository"
            } else {
                Logger.e(UserRepository.data)
            }
            DataRepository.kv.removeValueForKey(::uid.name)
        }
        adapter.loadMoreModule.setOnLoadMoreListener(this)

    }

    override fun onOutput() {
        super.onOutput()
        mMessenger.output(this) { pageEvent ->
            when (pageEvent.eventId) {
                PageEvent.EVENT_SHOW_LOADING -> {
                    showLoadingView()
                    updateView<LoadingViewDelegate>(ViewType.LOADING) {
                        updateMessage("加载中222...")
                    }
                }
                PageEvent.EVENT_SHOW_CONTENT -> {
                    showContentView()
                }

                PageEvent.EVENT_FINISH_REFRESH -> {
                    Logger.e("EVENT_FINISH_REFRESH")
                    binding.refreshLayout.finishRefresh()
                }
                PageEvent.EVENT_FINISH_LOAD_MORE -> {
                    adapter.loadMoreModule.loadMoreComplete()
                }
            }
        }

        mRequester.output(this) { mainEvent ->
            when (mainEvent.eventId) {
                MainEvent.EVENT_GET_DATA -> {
                    currentPage = mainEvent.result.currentPage
                    if (mainEvent.result.isFirst) {
                        Logger.e("111")
                        adapter.setNewInstance(mainEvent.result.list)
                        mMessenger.input(PageEvent(PageEvent.EVENT_SHOW_CONTENT))
                    } else {
                        if (mainEvent.result.currentPage > 1) {
                            Logger.e("222")
                            adapter.addData(mainEvent.result.list)
                            mMessenger.input(PageEvent(PageEvent.EVENT_FINISH_LOAD_MORE))
                        } else {
                            Logger.e("333")
                            adapter.setNewInstance(mainEvent.result.list)
                            mMessenger.input(PageEvent(PageEvent.EVENT_FINISH_REFRESH))
                        }
                    }
                }
            }
        }
    }

    override fun onInput() {
        super.onInput()
        mMessenger.input(PageEvent(PageEvent.EVENT_SHOW_LOADING))
        mRequester.input(MainEvent(MainEvent.EVENT_GET_DATA).apply {
            param.page = 1
            param.pageSize = 20
            param.isFirst = true
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mRequester.input(MainEvent(MainEvent.EVENT_GET_DATA).apply {
            param.page = 1
            param.pageSize = 20
            param.isFirst = false
        })
    }

    override fun onLoadMore() {
        mRequester.input(MainEvent(MainEvent.EVENT_GET_DATA).apply {
            param.page = currentPage + 1
            param.pageSize = 20
            param.isFirst = false
        })
    }

}


object DataRepository : MMKVOwner {
    var isFirstLaunch by mmkvBool(default = true)
    var bookInfo by mmkvParcelable(default = BookInfo())
    var count by mmkvInt(default = 0)
    var data by mmkvString(default = "")
    var uid by mmkvString(default = "")
}

object UserRepository : MMKVOwner {
    override val kv: MMKV = MMKV.mmkvWithID(uid)
    var data by mmkvString(default = "")
}