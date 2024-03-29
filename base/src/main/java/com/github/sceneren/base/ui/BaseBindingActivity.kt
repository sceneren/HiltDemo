package com.github.sceneren.base.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.KeyboardUtils
import com.dylanc.loadingstateview.Decorative
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate
import com.github.sceneren.base.R
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kunminx.architecture.ui.scope.ViewModelScope
import com.therouter.TheRouter

abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity(),
    LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative,
    ActivityBinding<VB> by ActivityBindingDelegate(), OnTitleBarListener {

    private val mViewModelScope = ViewModelScope()

    protected abstract fun onInitViewModel()

    protected abstract fun onInitView()

    protected open fun onInitData() {}

    abstract fun onInput()

    abstract fun onOutput()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheRouter.inject(this)
        setContentViewWithBinding()

        binding.root.decorate(this, this)

        onInitViewModel()
        onInitView()

        initImmersionBar()

        onInitData()
        onOutput()
        onInput()
    }

    private fun initImmersionBar() {
        immersionBar {
            keyboardEnable(true)
            statusBarDarkFont(!setStatusBarWhiteIcon())
            navigationBarDarkIcon(!setNavigationBarWhiteIcon())
            titleBar(injectTitleBar())
        }
    }

    open fun injectTitleBar(): TitleBar? {
        return null
    }

    open fun setStatusBarWhiteIcon(): Boolean {
        return false
    }

    open fun setNavigationBarWhiteIcon(): Boolean {
        return false
    }

    /**
     * TODO tip 2: Jetpack 通过 "工厂模式" 实现 ViewModel 作用域可控，
     * 目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
     * 值得注意的是，通过不同作用域 Provider 获得 ViewModel 实例非同一个，
     * 故若 ViewModel 状态信息保留不符合预期，可从该角度出发排查 是否眼前 ViewModel 实例非目标实例所致。
     * 如这么说无体会，详见 https://xiaozhuanlan.com/topic/6257931840
     */
    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getActivityScopeViewModel(this, modelClass)
    }

    protected open fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getApplicationScopeViewModel(modelClass)
    }

    protected fun showLoadingDialog(@StringRes resId: Int = R.string.base_loading) {
        WaitDialog.show(this, resId)
    }

    protected fun showLoadingDialog(message: String) {
        WaitDialog.show(this, message)
    }

    protected fun hideLoadingDialog() {
        WaitDialog.dismiss()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            v?.let {
                if (isShouldHideKeyboard(v, ev)) {
                    KeyboardUtils.hideSoftInput(this)
                    v.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideKeyboard(v: View, event: MotionEvent): Boolean {
        if ((v is EditText)) {
            val l = intArrayOf(0, 0)
            v.getLocationOnScreen(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.rawX > left && event.rawX < right
                    && event.rawY > top && event.rawY < bottom)
        }
        return false
    }

}
