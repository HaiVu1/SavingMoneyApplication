package haivv.learning.savingmoney.utils.binding

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import haivv.learning.savingmoney.R

@BindingAdapter("isEnable")
fun isEnable(materialButton: MaterialButton, isEnable: Boolean) {
    if (isEnable) {
        materialButton.isClickable = true
        materialButton.background = ContextCompat.getDrawable(
            materialButton.context,
            R.drawable.background_button_enable
        )
    } else {
        materialButton.isClickable = false
        materialButton.background = ContextCompat.getDrawable(
            materialButton.context,
            R.drawable.background_button_disable
        )
    }
}