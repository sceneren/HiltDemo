package wiki.scene.hiltdemo.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.SlidingTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import wiki.scene.hiltdemo.MainTabEntity

fun CommonTabLayout.bindViewPager2(
    act: FragmentActivity, vp2: ViewPager2, tabList: List<MainTabEntity>
) {
    val list = arrayListOf<CustomTabEntity>()
    list.addAll(tabList)
    setTabData(list)

    vp2.run {
        //是否可滑动
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

fun SlidingTabLayout.bindViewPage2(
    fragment: Fragment,
    vp2: ViewPager2,
    titleList: List<String>,
    fragmentList: MutableList<Fragment>
) {
    titleList.forEach {
        addNewTab(it)
    }


    vp2.run {
        //是否可滑动
        isUserInputEnabled = false
        offscreenPageLimit = 4
        adapter = object : FragmentStateAdapter(fragment) {
            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }

            override fun getItemCount(): Int {
                return titleList.size
            }

        }
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                this@bindViewPage2.currentTab = position
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