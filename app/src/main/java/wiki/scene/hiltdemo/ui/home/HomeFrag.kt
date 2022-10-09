package wiki.scene.hiltdemo.ui.home

import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.github.sceneren.base.event.BaseEvent
import com.github.sceneren.base.ui.BaseBindingFragment
import com.hjq.bar.TitleBar
import com.therouter.router.Autowired
import com.therouter.router.Route
import wiki.scene.hiltdemo.AF
import wiki.scene.hiltdemo.databinding.FragHomeBinding
import wiki.scene.hiltdemo.ext.bindViewPage2
import wiki.scene.hiltdemo.ui.home.event.HomeEvent
import wiki.scene.hiltdemo.ui.home.viewmodel.HomeViewModel

@Route(path = "/app/HomeFrag")
class HomeFrag : BaseBindingFragment<FragHomeBinding>() {

    @JvmField
    @Autowired
    var type: Int = 0

    @JvmField
    @Autowired
    var typeName: String = ""

    private lateinit var viewModel: HomeViewModel

    override fun injectTitleBar(): TitleBar {
        return binding.titleBar.apply {
            title = "主页-$typeName-$type"
        }
    }

    override fun onInitViewModel() {
        viewModel = getFragmentScopeViewModel(HomeViewModel::class.java)
    }

    override fun onInitView() {

    }

    override fun onOutput() {
        viewModel.output(this) {
            when (it.eventId) {
                BaseEvent.EVENT_SHOW_LOADING_PAGE -> {
                    showLoadingView()
                }
                BaseEvent.EVENT_SHOW_CONTENT_PAGE -> {
                    showContentView()
                }
                BaseEvent.EVENT_SHOW_ERROR_PAGE -> {
                    showErrorView()
                }
                HomeEvent.EVENT_PROJECT_TREE -> {
                    LogUtils.e(it.result.list)
                    val titleList = mutableListOf<String>()
                    val fragmentList = mutableListOf<Fragment>()
                    it.result.list.forEach { info ->
                        titleList.add(info.name)
                        fragmentList.add(AF.newInstance(info.id))
                    }
                    binding.magicIndicator.bindViewPage2(
                        this, binding.viewPager2, titleList
                    )
                }
            }
        }
    }

    override fun onInput() {
    }

    override fun lazyLoadData() {
        viewModel.input(HomeEvent(HomeEvent.EVENT_PROJECT_TREE))
    }

    override fun statusPageContentView(): View {
        return binding.contentView
    }


}