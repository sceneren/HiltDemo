package com.github.sceneren.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.KeyboardUtils
import com.dylanc.loadingstateview.Decorative
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate
import com.github.sceneren.base.R
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.TitleBar
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kunminx.architecture.ui.scope.ViewModelScope
import com.therouter.TheRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


abstract class BaseBindingFragment<VB : ViewBinding> : Fragment(),
    LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative,
    FragmentBinding<VB> by FragmentBindingDelegate() {
    private val mViewModelScope = ViewModelScope()

    protected lateinit var mActivity: AppCompatActivity

    //是否第一次加载
    private var isFirst: Boolean = true

    protected abstract fun onInitViewModel()

    protected abstract fun onInitView()

    protected open fun onInitData() {}

    abstract fun onOutput()

    abstract fun onInput()

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    override val contentView: View?
        get() = statusPageContentView()

    abstract fun statusPageContentView(): View?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheRouter.inject(this)
        onInitViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = createViewWithBinding(inflater, container)
        view.decorate(this, this)
        onInitView()
        initImmersionBar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        onInitData()
        onOutput()
        onInput()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿

            lifecycleScope.launch {
                delay(lazyLoadTime())
                lazyLoadData()
                isFirst = false
            }
        }
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 100
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

    protected open fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getFragmentScopeViewModel(this, modelClass)
    }

    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getFragmentScopeViewModel(this, modelClass)
    }

    protected open fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getApplicationScopeViewModel(modelClass)
    }

    protected fun showLoadingDialog(@StringRes resId: Int = R.string.base_loading) {
        WaitDialog.show(resId)
    }

    protected fun showLoadingDialog(message: String) {
        WaitDialog.show(message)
    }

    protected fun hideLoadingDialog() {
        WaitDialog.dismiss()
    }

    override fun onPause() {
        super.onPause()
        KeyboardUtils.clickBlankArea2HideSoftInput()
    }
}
