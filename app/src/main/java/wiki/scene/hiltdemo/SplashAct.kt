package wiki.scene.hiltdemo

import android.animation.Animator
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils
import com.github.sceneren.base.ui.BaseBindingActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import wiki.scene.hiltdemo.databinding.ActSplashBinding

class SplashAct : BaseBindingActivity<ActSplashBinding>(), Animator.AnimatorListener {

    override fun onInitViewModel() {
    }

    override fun onInitView() {
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
        }
        binding.lav.addAnimatorListener(this)
        onBackPressedDispatcher.handleOnBackPressed(this) {
            LogUtils.e("onBackPressed")
        }
    }

    override fun onAnimationStart(animation: Animator) {
    }

    override fun onAnimationEnd(animation: Animator) {
        startActivity(Intent(this, MainAct::class.java))
        finish()
    }

    override fun onAnimationCancel(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }

    override fun onInput() {
    }


    override fun onOutput() {

    }

}


fun OnBackPressedDispatcher.handleOnBackPressed(function: () -> Unit) {
    addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            function.invoke()
        }
    })
}

fun OnBackPressedDispatcher.handleOnBackPressed(
    lifecycleOwner: LifecycleOwner,
    function: () -> Unit
) {
    addCallback(lifecycleOwner, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            function.invoke()
        }
    })
}
