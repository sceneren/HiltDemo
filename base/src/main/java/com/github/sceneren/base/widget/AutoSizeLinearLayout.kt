package com.github.sceneren.base.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import me.jessyan.autosize.AutoSizeCompat

class AutoSizeLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        //如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensityOfGlobal(resources)
        return super.generateLayoutParams(attrs)
    }
}