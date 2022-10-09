package wiki.scene.hiltdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.github.sceneren.base.event.BaseEvent
import com.github.sceneren.base.event.BaseRecycleViewEvent
import com.github.sceneren.base.ui.BaseBindingFragment
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.therouter.TheRouter
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.adapter.MainAdapter
import wiki.scene.hiltdemo.databinding.FragABinding
import wiki.scene.hiltdemo.event.MainEvent
import wiki.scene.hiltdemo.hilt.factory.MainAdapterFactory
import wiki.scene.hiltdemo.mmkv.UserRepository
import wiki.scene.hiltdemo.requester.MainListRequester
import javax.inject.Inject

@AndroidEntryPoint
class AF : BaseBindingFragment<FragABinding>(), OnRefreshListener,
    OnLoadMoreListener {
    private var type: Int = 0

    @Inject
    lateinit var mainAdapterFactory: MainAdapterFactory

//    @Inject
//    lateinit var userRepositoryFactory: UserRepositoryFactory

    private lateinit var userRepository: UserRepository

//    @Inject
//    lateinit var dataRepository: DataRepository

    private lateinit var mRequester: MainListRequester
    private lateinit var adapter: MainAdapter

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

    override fun statusPageContentView(): View {
        return binding.refreshLayout
    }

    override fun onInitViewModel() {
        mRequester = getFragmentScopeViewModel(MainListRequester::class.java)
    }


    override fun onInitView() {
        type = requireArguments().getInt("type", 0)
//        userRepository = userRepositoryFactory.createUserRepository(dataRepository.userInfo)


        binding.refreshLayout.setOnRefreshListener(this)
        adapter = mainAdapterFactory.createMainAdapter(type)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter.setOnItemClickListener { _, _, position ->
//            dataRepository.userInfo = UserInfo(
//                uid = position.toString(),
//                name = "name$position",
//            )
//            if (position == 0) {
//                dataRepository.data = "DataRepository"
//            } else {
//                Logger.e("dataRepository.data = ${dataRepository.data}")
//            }
            //dataRepository.kv.removeValueForKey(dataRepository::userInfo.name)

            LogUtils.e("====>title2:${adapter.data[position].title}")
            TheRouter.build("/app/RecyclerViewActivity")
                .withInt("id", position)
                .withString("title", adapter.data[position].title)
                .navigation()

        }
        adapter.loadMoreModule.setOnLoadMoreListener(this)

    }

    override fun onOutput() {

        mRequester.output(this) { mainEvent ->
            when (mainEvent.eventId) {
                BaseEvent.EVENT_SHOW_LOADING_PAGE -> {
                    showLoadingView()
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

    override fun lazyLoadData() {
        mRequester.input(MainEvent(MainEvent.EVENT_REFRESH_DATA).apply {
            param.page = 315
            param.isFirst = true
        })
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("$type====>onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.e("$type====>onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.e("$type====>onDestroyView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.e("$type====>onCreate")
    }

}