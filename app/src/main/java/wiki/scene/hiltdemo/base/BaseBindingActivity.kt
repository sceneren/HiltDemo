package wiki.scene.hiltdemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.Decorative
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate
import com.kunminx.architecture.ui.scope.ViewModelScope

abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity(),
    LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative,
    ActivityBinding<VB> by ActivityBindingDelegate() {

    private val mViewModelScope = ViewModelScope()

    protected abstract fun onInitViewModel()

    protected abstract fun onInitView()

    protected open fun onInitData() {}

    protected open fun onOutput() {}

    protected open fun onInput() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithBinding()
        binding.root.decorate(this, this)
        onInitViewModel()
        onInitView()
        onInitData()
        onOutput()
        onInput()
    }

    //TODO tip 2: Jetpack 通过 "工厂模式" 实现 ViewModel 作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域 Provider 获得 ViewModel 实例非同一个，
    //故若 ViewModel 状态信息保留不符合预期，可从该角度出发排查 是否眼前 ViewModel 实例非目标实例所致。
    //如这么说无体会，详见 https://xiaozhuanlan.com/topic/6257931840
    protected open fun <T : ViewModel?> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getActivityScopeViewModel<T>(this, modelClass)
    }

    protected open fun <T : ViewModel?> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getApplicationScopeViewModel<T>(modelClass)
    }
}
