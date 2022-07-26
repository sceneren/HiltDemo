package wiki.scene.hiltdemo

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.mmkv.*
import com.orhanobut.logger.Logger
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.DataRepository.uid
import wiki.scene.hiltdemo.base.BaseBindingActivity
import wiki.scene.hiltdemo.databinding.ActivityMainBinding
import wiki.scene.hiltdemo.hilt.factory.MainAdapterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    @Inject
    lateinit var mainAdapterFactory: MainAdapterFactory

    @Inject
    lateinit var list: MutableList<BookInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = mainAdapterFactory.createMainAdapter(1)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, _, position ->
            uid = position.toString()
            if (position == 0) {
                DataRepository.data = "DataRepository"
                UserRepository.data = "UserRepository"
            } else {
                Logger.e(UserRepository.data)
            }
            DataRepository.kv.removeValueForKey(::uid.name)
        }

        showLoadingView()

        Handler().postDelayed({
            runOnUiThread {
                showContentView()
            }
        }, 5000)
    }

    override val contentView: View
        get() = binding.recyclerView

}

object DataRepository : MMKVOwner {
    var isFirstLaunch by mmkvBool(default = true)
    var bookInfo by mmkvParcelable(default = BookInfo())
    var count by mmkvInt(default = 0)
    var data by mmkvString(default = "")
    var uid by mmkvString(default = "")
}

object UserRepository : MMKVOwner {
    override val kv: MMKV = MMKV.mmkvWithID(uid)
    var data by mmkvString(default = "")
}