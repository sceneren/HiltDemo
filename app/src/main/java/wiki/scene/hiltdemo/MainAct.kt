package wiki.scene.hiltdemo

import com.github.sceneren.base.ui.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.databinding.ActMainBinding
import wiki.scene.hiltdemo.ext.bindViewPager2

@AndroidEntryPoint
class MainAct : BaseBindingActivity<ActMainBinding>() {

    override fun onInitView() {

        binding.tlTab.bindViewPager2(
            this, binding.viewPager2, arrayListOf(
                MainTabEntity(
                    "标题1",
                    R.drawable.tab_home_select,
                    R.drawable.tab_home_unselect,
                    AF.newInstance(0)
                ),
                MainTabEntity(
                    "标题2",
                    R.drawable.tab_home_select,
                    R.drawable.tab_home_unselect,
                    AF.newInstance(1)
                ),
                MainTabEntity(
                    "标题3",
                    R.drawable.tab_home_select,
                    R.drawable.tab_home_unselect,
                    AF.newInstance(2)
                ),
                MainTabEntity(
                    "标题4",
                    R.drawable.tab_home_select,
                    R.drawable.tab_home_unselect,
                    AF.newInstance(3)
                )
            )
        )

    }

    override fun onInitViewModel() {

    }

}