package haivv.learning.savingmoney.utils.binding

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("showError")
fun showError(view: TextView, messageError: String?) {
    if (TextUtils.isEmpty(messageError)) {
        view.visibility = View.GONE
    } else {
        view.run {
            visibility = View.VISIBLE
            text = messageError
        }
    }
}

