package haivv.learning.savingmoney.utils.binding

import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.utils.ValidationValueState


fun handleFocusChange(
    editText: EditText,
    validationValueState: LiveData<ValidationValueState>,
    lifecycleOwner: LifecycleOwner
) {
    editText.setOnFocusChangeListener { view, hasFocus ->
        validationValueState.observe(lifecycleOwner) { validationValueState ->
            if (!hasFocus) {
                if (validationValueState == ValidationValueState.Validate) {
                    view.background = ContextCompat.getDrawable(
                        editText.context,
                        R.drawable.background_edit_text
                    )
                } else {
                    view.background = ContextCompat.getDrawable(
                        editText.context,
                        R.drawable.background_edittext_error
                    )
                }
            } else {
                when (validationValueState) {
                    ValidationValueState.Validate -> {
                        view.background = ContextCompat.getDrawable(
                            editText.context,
                            R.drawable.background_edittext_focus
                        )
                    }
                    ValidationValueState.Empty -> {
                        view.background = ContextCompat.getDrawable(
                            editText.context,
                            R.drawable.background_edittext_error
                        )
                    }
                    else -> {
                        view.background = ContextCompat.getDrawable(
                            editText.context,
                            R.drawable.background_edit_text
                        )
                    }
                }
            }
        }
    }
}

fun handleValidationFocusChange(
    editText: EditText,
    validationValueState: LiveData<ValidationValueState>,
    lifecycleOwner: LifecycleOwner
) {
    editText.setOnFocusChangeListener { view, hasFocus ->
        validationValueState.observe(lifecycleOwner) { validationValueState ->
            if (!hasFocus) {
                if (validationValueState == ValidationValueState.Validate) {
                    view.background = ContextCompat.getDrawable(
                        editText.context,
                        R.drawable.background_edit_text
                    )
                } else {
                    view.background = ContextCompat.getDrawable(
                        editText.context,
                        R.drawable.background_edittext_error
                    )
                }
            } else {
                when (validationValueState) {
                    ValidationValueState.Validate -> {
                        view.background = ContextCompat.getDrawable(
                            editText.context,
                            R.drawable.background_edittext_focus
                        )
                    }
                    ValidationValueState.Empty -> {
                        view.background = ContextCompat.getDrawable(
                            editText.context,
                            R.drawable.background_edittext_error
                        )
                    }
                    ValidationValueState.Invalidate -> {
                        view.background = ContextCompat.getDrawable(
                            editText.context,
                            R.drawable.background_edittext_error
                        )
                    }
                    else -> {
                        view.background = ContextCompat.getDrawable(
                            editText.context,
                            R.drawable.background_edit_text
                        )
                    }
                }
            }
        }
    }
}