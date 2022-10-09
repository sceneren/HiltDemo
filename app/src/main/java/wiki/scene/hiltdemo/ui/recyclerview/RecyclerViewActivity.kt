package wiki.scene.hiltdemo.ui.recyclerview

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.dylanc.loadingstateview.ViewType
import com.github.sceneren.base.event.BaseEvent
import com.github.sceneren.base.event.BaseRecycleViewEvent
import com.github.sceneren.base.state.LoadingViewDelegate
import com.github.sceneren.base.ui.BaseBindingActivity
import com.hjq.bar.TitleBar
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.therouter.router.Autowired
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.adapter.MainAdapter
import wiki.scene.hiltdemo.databinding.ActivityRecyclerViewBinding
import wiki.scene.hiltdemo.event.MainEvent
import wiki.scene.hiltdemo.hilt.factory.MainAdapterFactory
import wiki.scene.hiltdemo.requester.MainListRequester
import javax.inject.Inject

@Route(path = "/app/RecyclerViewActivity")
@AndroidEntryPoint
class RecyclerViewActivity : BaseBindingActivity<ActivityRecyclerViewBinding>(), OnRefreshListener,
    OnLoadMoreListener {

    @JvmField
    @Autowired
    var id: Int = 0

    @JvmField
    @Autowired
    var title: String = ""

    @Inject
    lateinit var mainAdapterFactory: MainAdapterFactory

    private lateinit var mRequester: MainListRequester
    private lateinit var adapter: MainAdapter

    override val contentView: View
        get() = binding.refreshLayout

    private var currentPage = 1

    override fun onInitViewModel() {
        mRequester = getActivityScopeViewModel(MainListRequester::class.java)
    }

    override fun onInitView() {

        binding.refreshLayout.setOnRefreshListener(this)
        adapter = mainAdapterFactory.createMainAdapter(1)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener { _, _, position ->
        }
        adapter.loadMoreModule.setOnLoadMoreListener(this)

        binding.titleBar.setOnTitleBarListener(this)
        LogUtils.e("====>title2:$title")
        binding.titleBar.title = "$id-$title"
    }

    override fun injectTitleBar(): TitleBar {
        return binding.titleBar
    }

    override fun onLeftClick(titleBar: TitleBar?) {
        super.onLeftClick(titleBar)
        onBackPressedDispatcher.onBackPressed()
    }


    override fun onOutput() {

        mRequester.output(this) { mainEvent ->
            when (mainEvent.eventId) {
                BaseEvent.EVENT_SHOW_LOADING_PAGE -> {
                    showLoadingView()
                    updateView<LoadingViewDelegate>(ViewType.LOADING) {
                        updateMessage("加载中...")
                    }
                }
                BaseEvent.EVENT_SHOW_CONTENT_PAGE -> {
                    showContentView()
                }
                BaseEvent.EVENT_SHOW_ERROR_PAGE -> {
                    showErrorView()
                }
                BaseEvent.EVENT_SHOW_LOADING_DIALOG -> {
                    showLoadingDialog()
                }
                BaseEvent.EVENT_HIDE_LOADING_DIALOG -> {
                    hideLoadingDialog()
                }
                BaseEvent.EVENT_SHOW_TOAST -> {
                    LogUtils.e(mainEvent.result.errorMsg)
                }
                BaseRecycleViewEvent.EVENT_FINISH_REFRESH_SUCCESS -> {
                    binding.refreshLayout.finishRefresh(true)
                }
                BaseRecycleViewEvent.EVENT_FINISH_REFRESH_FAIL -> {
                    binding.refreshLayout.finishRefresh(false)
                }
                BaseRecycleViewEvent.EVENT_FINISH_LOAD_MORE_COMPLETE -> {
                    adapter.loadMoreModule.loadMoreComplete()
                }
                BaseRecycleViewEvent.EVENT_FINISH_LOAD_MORE_END -> {
                    adapter.loadMoreModule.loadMoreEnd()
                }
                BaseRecycleViewEvent.EVENT_FINISH_LOAD_MORE_FAIL -> {
                    adapter.loadMoreModule.loadMoreFail()
                }
                MainEvent.EVENT_ADD_DATA -> {
                    currentPage = mainEvent.result.currentPage
                    adapter.addData(mainEvent.result.list)
                }
                MainEvent.EVENT_REFRESH_DATA -> {
                    currentPage = mainEvent.result.currentPage
                    adapter.setNewInstance(mainEvent.result.list)
                }
            }
        }
    }

    override fun onInput() {
        mRequester.input(MainEvent(MainEvent.EVENT_REFRESH_DATA).apply {
            param.page = 315
            param.isFirst = true
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mRequester.input(MainEvent(MainEvent.EVENT_REFRESH_DATA).apply {
            param.page = 1
            param.isFirst = false
        })
    }

    override fun onLoadMore() {
        mRequester.input(MainEvent(MainEvent.EVENT_ADD_DATA).apply {
            param.page = currentPage + 1
            param.isFirst = false
        })
    }

}