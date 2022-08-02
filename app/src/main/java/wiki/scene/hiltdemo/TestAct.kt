package wiki.scene.hiltdemo

import android.content.Intent
import com.github.sceneren.base.ui.BaseBindingActivity
import wiki.scene.hiltdemo.databinding.ActTestBinding

class TestAct : BaseBindingActivity<ActTestBinding>() {

    override fun onInitViewModel() {
    }

    override fun onInitView() {
        binding.button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}