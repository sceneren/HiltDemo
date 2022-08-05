package wiki.scene.hiltdemo.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import wiki.scene.hiltdemo.MainTabEntity

fun CommonTabLayout.bindViewPager2(
    act: FragmentActivity,
    vp2: ViewPager2,
    tabList: List<MainTabEntity>
) {
    val list = arrayListOf<CustomTabEntity>()
    list.addAll(tabList)
    setTabData(list)

    //是否可滑动
    vp2.run {

        isUserInputEnabled = false
        offscreenPageLimit = 4
        adapter = object : FragmentStateAdapter(act) {
            override fun createFragment(position: Int): Fragment {
                return tabList[position].fragment
            }

            override fun getItemCount(): Int {
                return tabList.size
            }

        }
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                this@bindViewPager2.currentTab = position
            }
        })

    }

    setOnTabSelectListener(object : OnTabSelectListener {
        override fun onTabSelect(position: Int) {
            vp2.setCurrentItem(position, false)
        }

        override fun onTabReselect(position: Int) {
        }
    })

}