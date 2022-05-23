package wiki.scene.hiltdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
    }
}