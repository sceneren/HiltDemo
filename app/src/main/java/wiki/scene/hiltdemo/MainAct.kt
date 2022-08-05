package wiki.scene.hiltdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.OnTabSelectListener
import com.github.sceneren.base.ui.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.databinding.ActMainBinding

@AndroidEntryPoint
class MainAct : BaseBindingActivity<ActMainBinding>() {

    override fun onInitView() {

        binding.viewPager2.initMain(this)
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tlTab.currentTab = position
            }
        })

        binding.tlTab.setTabData(
            arrayListOf(
                MainTabEntity("标题1", R.drawable.tab_home_select, R.drawable.tab_home_unselect),
                MainTabEntity("标题2", R.drawable.tab_home_select, R.drawable.tab_home_unselect),
                MainTabEntity("标题3", R.drawable.tab_home_select, R.drawable.tab_home_unselect),
                MainTabEntity("标题4", R.drawable.tab_home_select, R.drawable.tab_home_unselect)
            )
        )
        binding.tlTab.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                binding.viewPager2.currentItem = position
            }

            override fun onTabReselect(position: Int) {
            }
        })

    }

    override fun onInitViewModel() {

    }


}

fun ViewPager2.initMain(act: FragmentActivity): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = false
    this.offscreenPageLimit = 4
    //设置适配器
    adapter = object : FragmentStateAdapter(act) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return AF.newInstance(0)
                }
                1 -> {
                    return AF.newInstance(1)
                }
                2 -> {
                    return AF.newInstance(2)
                }
                3 -> {
                    return AF.newInstance(3)
                }
                4 -> {
                    return AF.newInstance(4)
                }
                else -> {
                    return AF.newInstance(0)
                }
            }
        }

        override fun getItemCount() = 4
    }

    return this
}