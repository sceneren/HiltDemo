package wiki.scene.hiltdemo.widget.indicator

import android.content.Context
import android.graphics.Color
import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class LineNavigatorAdapter(private val titleList: List<String>, private val vp2: ViewPager2) :
    CommonNavigatorAdapter() {
    override fun getCount(): Int {
        return titleList.size
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
        simplePagerTitleView.normalColor = Color.GRAY
        simplePagerTitleView.selectedColor = Color.CYAN
        simplePagerTitleView.text = titleList[index]
        simplePagerTitleView.setOnClickListener { vp2.currentItem = index }
        return simplePagerTitleView
    }

    override fun getIndicator(context: Context?): IPagerIndicator {
        val linePagerIndicator = LinePagerIndicator(context)
        linePagerIndicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        linePagerIndicator.roundRadius = 5f
        linePagerIndicator.setColors(Color.CYAN)
        return linePagerIndicator
    }
}