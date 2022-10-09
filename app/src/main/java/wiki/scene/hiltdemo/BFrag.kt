package wiki.scene.hiltdemo

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.github.sceneren.base.ui.BaseBindingFragment
import wiki.scene.hiltdemo.databinding.FragBBinding


class BFrag : BaseBindingFragment<FragBBinding>() {
    companion object {
        fun newInstance(type: Int): BFrag {
            val args = Bundle()
            args.putInt("type", type)
            val fragment = BFrag()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onInitViewModel() {

    }

    override fun onInitView() {
        binding.customView.addControlClickListener(
            onCenterMenuClick = {
                ToastUtils.showShort("中间")
            },
            onLeftMenuClick = {
                ToastUtils.showShort("左边")
            },
            onRightMenuClick = {
                ToastUtils.showShort("右边")
            },
            onTopMenuClick = {
                ToastUtils.showShort("上边")
            },
            onBottomMenuClick = {
                ToastUtils.showShort("下边")
            }
        )

    }

    override fun onOutput() {
    }

    override fun onInput() {
    }

    override fun lazyLoadData() {
    }

    override fun statusPageContentView(): View {
        return binding.root
    }
}