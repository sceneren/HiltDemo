package wiki.scene.hiltdemo.ui.main

import com.github.sceneren.base.ui.BaseBindingActivity
import com.therouter.TheRouter
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.AF
import wiki.scene.hiltdemo.BFrag
import wiki.scene.hiltdemo.MainTabEntity
import wiki.scene.hiltdemo.R
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
                    TheRouter.build("/app/HomeFrag")
                        .withString("typeName", "这是标题")
                        .withInt("type", 0)
                        .createFragment()!!
                ), MainTabEntity(
                    "标题2",
                    R.drawable.tab_home_select,
                    R.drawable.tab_home_unselect,
                    BFrag.newInstance(1)
                ), MainTabEntity(
                    "标题3",
                    R.drawable.tab_home_select,
                    R.drawable.tab_home_unselect,
                    AF.newInstance(2)
                ), MainTabEntity(
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

    override fun onInput() {

    }

    override fun onOutput() {
    }

}