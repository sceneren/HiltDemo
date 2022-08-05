package wiki.scene.hiltdemo

import com.github.sceneren.base.ui.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.databinding.ActMainBinding

@AndroidEntryPoint
class MainAct : BaseBindingActivity<ActMainBinding>() {

    override fun onInitView() {

        binding.tlTab.setTabData(
            arrayListOf(
                MainTabEntity("首页", R.drawable.tab_home_select, R.drawable.tab_home_unselect),
                MainTabEntity("首页", R.drawable.tab_home_select, R.drawable.tab_home_unselect),
                MainTabEntity("首页", R.drawable.tab_home_select, R.drawable.tab_home_unselect),
                MainTabEntity("首页", R.drawable.tab_home_select, R.drawable.tab_home_unselect)
            ),
            this,
            R.id.fl_container,
            arrayListOf(
                AF.newInstance(0),
                AF.newInstance(1),
                AF.newInstance(2),
                AF.newInstance(3)
            )
        )
    }

    override fun onInitViewModel() {

    }


}