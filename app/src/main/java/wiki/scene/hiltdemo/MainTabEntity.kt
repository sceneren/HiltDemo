package wiki.scene.hiltdemo

import androidx.annotation.DrawableRes
import com.flyco.tablayout.listener.CustomTabEntity

class MainTabEntity(
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
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