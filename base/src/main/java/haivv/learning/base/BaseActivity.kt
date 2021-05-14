package haivv.learning.base

import android.os.Bundle
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    lateinit var viewBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())
    }

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onDestroy() {
        viewBinding.unbind()
        super.onDestroy()
    }

}