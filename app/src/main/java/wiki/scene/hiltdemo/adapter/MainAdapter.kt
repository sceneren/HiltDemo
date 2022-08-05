package wiki.scene.hiltdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import wiki.scene.hiltdemo.R
import wiki.scene.hiltdemo.entity.ArticleInfo

class MainAdapter @AssistedInject constructor(@Assisted val type: Int) :
    BaseQuickAdapter<ArticleInfo, BaseViewHolder>(R.layout.activity_recycler_view_item),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ArticleInfo) {
        holder.setText(R.id.tv_title, item.title)
    }

}