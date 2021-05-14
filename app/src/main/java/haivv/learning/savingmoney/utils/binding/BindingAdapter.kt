package haivv.learning.savingmoney.utils.binding

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import haivv.learning.savingmoney.R

@BindingAdapter("setVisible")
fun setVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("convertGender")
fun convertGender(view: TextView, genderInt: Int) {
    if (genderInt == 0) {
        view.text = view.resources.getText(R.string.registration_female)
    } else {
        view.text = view.resources.getText(R.string.registration_male)
    }
}