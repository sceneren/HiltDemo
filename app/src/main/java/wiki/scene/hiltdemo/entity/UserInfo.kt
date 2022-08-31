package wiki.scene.hiltdemo.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    var name: String = "",
    var age: Int = 0,
    var uid: String = ""
) : Parcelable