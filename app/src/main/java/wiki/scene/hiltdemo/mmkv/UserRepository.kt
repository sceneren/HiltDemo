package wiki.scene.hiltdemo.mmkv

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvString
import com.tencent.mmkv.MMKV
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import wiki.scene.hiltdemo.entity.UserInfo

class UserRepository @AssistedInject constructor(@Assisted userInfo: UserInfo) : MMKVOwner {

    override val kv: MMKV = MMKV.mmkvWithID(userInfo.uid)
    var data by mmkvString(default = "")
}