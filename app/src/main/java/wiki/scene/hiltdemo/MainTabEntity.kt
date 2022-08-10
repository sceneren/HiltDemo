package wiki.scene.hiltdemo

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity

class MainTabEntity(
    private val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
    val fragment: Fragment
) : CustomTabEntity {
    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }


}