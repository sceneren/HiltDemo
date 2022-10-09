package wiki.scene.hiltdemo.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import wiki.scene.hiltdemo.AF
import wiki.scene.hiltdemo.MainTabEntity
import wiki.scene.hiltdemo.widget.indicator.LineNavigatorAdapter

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

fun MagicIndicator.bindViewPage2(
    fragment: Fragment,
    viewPager2: ViewPager2,
    titleList: List<String>,
//    fragmentList: List<Fragment>
) {
    this.navigator = CommonNavigator(context).apply {
        adapter = LineNavigatorAdapter(titleList, viewPager2)
    }

    viewPager2.run {
        offscreenPageLimit = titleList.size
        adapter = object : FragmentStateAdapter(fragment) {
            override fun createFragment(position: Int): Fragment {
                return AF.newInstance(position)
            }

            override fun getItemCount(): Int {
                return titleList.size
            }

        }

        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                this@bindViewPage2.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                this@bindViewPage2.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                this@bindViewPage2.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

}

fun MagicIndicator.bindViewPage2(
    activity: FragmentActivity,
    viewPager2: ViewPager2,
    titleList: List<String>,
    fragmentList: List<Fragment>
) {
    this.navigator = CommonNavigator(context).apply {
        LineNavigatorAdapter(titleList, viewPager2)
    }

    viewPager2.run {
        adapter = object : FragmentStateAdapter(activity) {
            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }

            override fun getItemCount(): Int {
                return fragmentList.size
            }

        }

        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                this@bindViewPage2.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                this@bindViewPage2.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                this@bindViewPage2.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

}