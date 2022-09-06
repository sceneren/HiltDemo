package wiki.scene.hiltdemo

import android.animation.Animator
import android.content.Intent
import com.github.sceneren.base.ui.BaseBindingActivity
import com.gyf.immersionbar.ktx.immersionBar
import wiki.scene.hiltdemo.databinding.ActSplashBinding

class SplashAct : BaseBindingActivity<ActSplashBinding>(), Animator.AnimatorListener {

    override fun onInitViewModel() {
    }

    override fun onInitView() {
        immersionBar {
            statusBarColor(R.color.white)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
        binding.lav.addAnimatorListener(this)
    }

    override fun onAnimationStart(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
        startActivity(Intent(this, MainAct::class.java))
        finish()
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onBackPressed() {

    }

    override fun onInput() {
    }


    override fun onOutput() {

    }

}