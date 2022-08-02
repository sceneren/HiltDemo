package com.github.sceneren.base.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.github.sceneren.base.R

class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {
    private lateinit var tvMsg: TextView

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.layout_loading, parent, false)
        tvMsg = view.findViewById(R.id.tv_msg)
        return view
    }


    fun updateMessage(message: String?) {
        message?.let {
            tvMsg.text = message
        }
    }

    fun updateMessage(@StringRes resId: Int) {
        tvMsg.setText(resId)
    }
}
