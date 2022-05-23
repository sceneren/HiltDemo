package wiki.scene.hiltdemo

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class TestInfo @Inject constructor(val bookInfo: BookInfo,@ActivityContext val context: Context) {
}