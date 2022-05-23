package wiki.scene.hiltdemo.adapter

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import wiki.scene.hiltdemo.BookInfo
import wiki.scene.hiltdemo.R

class MainAdapter @AssistedInject constructor(@Assisted val type: Int) :
    BaseQuickAdapter<BookInfo, BaseViewHolder>(R.layout.activity_main_item) {
    init {
        Log.e("MainAdapter", "type:$type")
    }
    override fun convert(holder: BaseViewHolder, item: BookInfo) {
        holder.setText(R.id.tv_title, item.name)
    }

}