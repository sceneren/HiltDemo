package wiki.scene.hiltdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.mmkv.*
import com.orhanobut.logger.Logger
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.hiltdemo.databinding.ActivityMainBinding
import wiki.scene.hiltdemo.hilt.factory.MainAdapterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainAdapterFactory: MainAdapterFactory

    @Inject
    lateinit var list: MutableList<BookInfo>


    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val adapter = mainAdapterFactory.createMainAdapter(1)
        mBinding.recyclerView.adapter = adapter
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, _, position ->
            DataRepository.uid = position.toString()
            if (position == 0) {
                DataRepository.data = "DataRepository"
                UserRepository.data = "UserRepository"
            } else {
                Logger.e(UserRepository.data)
            }
        }
    }
}

object DataRepository : MMKVOwner {
    var isFirstLaunch by mmkvBool(default = true)
    var bookInfo by mmkvParcelable(default = BookInfo())
    var count by mmkvInt(default = 0)
    var data by mmkvString(default = "")
    var uid by mmkvString(default = "")
}

object UserRepository : MMKVOwner {
    override val kv: MMKV = MMKV.mmkvWithID(DataRepository.uid)
    var data by mmkvString(default = "")
}