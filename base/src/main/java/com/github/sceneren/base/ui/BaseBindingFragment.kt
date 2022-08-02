package com.github.sceneren.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.Decorative
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate
import com.kunminx.architecture.ui.scope.ViewModelScope


abstract class BaseBindingFragment<VB : ViewBinding> : Fragment(),
    LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative,
    FragmentBinding<VB> by FragmentBindingDelegate() {
    private val mViewModelScope = ViewModelScope()
    protected lateinit var mActivity: AppCompatActivity
    protected abstract fun onInitViewModel()

    open fun onInitView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?
    ): View {
        return createViewWithBinding(inflater, container).decorate(this, this)
    }

    protected open fun onInitData() {}

    protected open fun onOutput() {}

    protected open fun onInput() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return onInitView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitData()
        onOutput()
        onInput()
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
}
