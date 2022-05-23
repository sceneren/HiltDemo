package wiki.scene.hiltdemo.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import wiki.scene.hiltdemo.BookInfo

@Module
@InstallIn(ActivityComponent::class)
class BookModule {

    @Provides
    fun provideBook() = BookInfo("Android")

    @Provides
    fun provideBookInfoList(): MutableList<BookInfo> {
        val list = mutableListOf<BookInfo>()
        for (i in 0..10) {
            list.add(BookInfo("Android $i"))
        }
        return list
    }
}