package com.github.sceneren.base.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.github.sceneren.base.R

class ErrorViewDelegate : LoadingStateView.ViewDelegate(ViewType.ERROR) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.layout_error, parent, false).apply {
            findViewById<View>(R.id.iv_error).setOnClickListener {
                reload()
            }
            findViewById<View>(R.id.tv_msg).setOnClickListener {
                reload()
            }
        }

    fun reload() {
        onReloadListener?.onReload()
    }
}
