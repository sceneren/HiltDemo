package wiki.scene.hiltdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.dylanc.loadingstateview.ViewType
import com.github.sceneren.base.event.BaseEvent
import com.github.sceneren.base.event.BaseRecycleViewEvent
import com.github.sceneren.base.state.LoadingViewDelegate
import com.github.sceneren.base.ui.BaseBindingFragment
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.adapter.MainAdapter
import wiki.scene.hiltdemo.databinding.FragABinding
import wiki.scene.hiltdemo.event.MainEvent
import wiki.scene.hiltdemo.hilt.factory.MainAdapterFactory
import wiki.scene.hiltdemo.requester.MainListRequester
import javax.inject.Inject

@AndroidEntryPoint
class AF : BaseBindingFragment<FragABinding>(), OnRefreshListener,
    OnLoadMoreListener {
    private var type: Int = 0

    @Inject
    lateinit var mainAdapterFactory: MainAdapterFactory

    private lateinit var mRequester: MainListRequester
    private lateinit var adapter: MainAdapter

    override val contentView: View
        get() = binding.refreshLayout

    private var currentPage = 1


    companion object {
        fun newInstance(type: Int): AF {
            val args = Bundle()
            args.putInt("type", type)
            val fragment = AF()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onInitViewModel() {
        mRequester = getActivityScopeViewModel(MainListRequester::class.java)
    }

    override fun onInitView() {
        binding.refreshLayout.setOnRefreshListener(this)
        adapter = mainAdapterFactory.createMainAdapter(1)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter.setOnItemClickListener { _, _, position ->
            DataRepository.uid = position.toString()
            if (position == 0) {
                DataRepository.data = "DataRepository"
                UserRepository.data = "UserRepository"
            } else {
                Logger.e(UserRepository.data)
            }
            DataRepository.kv.removeValueForKey(DataRepository::uid.name)
        }
        adapter.loadMoreModule.setOnLoadMoreListener(this)


    }

    override fun onOutput() {
        super.onOutput()

        mRequester.output(this) { mainEvent ->
            when (mainEvent.eventId) {
                BaseEvent.EVENT_SHOW_LOADING_PAGE -> {
                    showLoadingView()
                    updateView<LoadingViewDelegate>(ViewType.LOADING) {
                        updateMessage("加载中222...")
                    }
                }
                BaseEvent.EVENT_SHOW_CONTENT_PAGE -> {
                    showContentView()
                }
                BaseEvent.EVENT_SHOW_ERROR_PAGE -> {
                    showErrorView()
                }
                BaseEvent.EVENT_SHOW_LOADING_DIALOG -> {
                    Logger.e("showLoadingDialog")
                }
                BaseEvent.EVENT_HIDE_LOADING_DIALOG -> {
                    Logger.e("hideLoadingDialog")
                }
                BaseEvent.EVENT_SHOW_TOAST -> {
                    Toast.makeText(activity, mainEvent.result.errorMsg, Toast.LENGTH_SHORT).show()
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
                MainEvent.EVENT_GET_DATA -> {
                    currentPage = mainEvent.result.currentPage

                    if (mainEvent.result.currentPage > 1) {
                        adapter.addData(mainEvent.result.list)
                    } else {
                        adapter.setNewInstance(mainEvent.result.list)
                    }

                }
            }
        }
    }

    override fun onInput() {
        super.onInput()
        mRequester.input(MainEvent(MainEvent.EVENT_GET_DATA).apply {
            param.page = 315
            param.isFirst = true
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mRequester.input(MainEvent(MainEvent.EVENT_GET_DATA).apply {
            param.page = 1
            param.isFirst = false
        })
    }

    override fun onLoadMore() {
        mRequester.input(MainEvent(MainEvent.EVENT_GET_DATA).apply {
            param.page = currentPage + 1
            param.isFirst = false
        })
    }

}