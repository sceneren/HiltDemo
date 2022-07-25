package wiki.scene.hiltdemo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookInfo(
    var name: String = ""
) : Parcelable