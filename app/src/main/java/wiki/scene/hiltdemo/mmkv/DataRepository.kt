package wiki.scene.hiltdemo.mmkv

import com.dylanc.mmkv.*
import wiki.scene.hiltdemo.entity.UserInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor() : MMKVOwner {
    var isFirstLaunch by mmkvBool(default = true)
    var count by mmkvInt(default = 0)
    var data by mmkvString(default = "")
    var userInfo by mmkvParcelable(default = UserInfo())
}