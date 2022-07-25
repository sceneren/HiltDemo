package wiki.scene.hiltdemo

import com.orhanobut.logger.AndroidLogAdapter

class CustomAndroidLogAdapter: AndroidLogAdapter() {
    override fun isLoggable(priority: Int, tag: String?): Boolean {
        return BuildConfig.DEBUG
    }
}