package haivv.learning.savingmoney.utils.binding

import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.utils.ValidationValueState

/**
 * Set edittext focus change background color
 *
 * @param editText Edittext
 * @param validationValueState livedata save state validation input
 * @param lifecycleOwner lifecycleOwner for livedata validationValueState
 */
fun handleFocusChange(
    editText: EditText,
    validationValueState: LiveData<ValidationValueState>,
    lifecycleOwner: LifecycleOwner
) {
    editText.setOnFocusChangeListener { view, hasFocus ->
        validationValueState.observe(lifecycleOwner) { validationValueState ->
            view.background = ContextCompat.getDrawable(
                editText.context,
                getBackgroundColorEditText(validationValueState, hasFocus)
            )
        }
    }
}

/**
 * Get background color by validation state
 *
 * @param validationState : State Validate, Empty, Invalidate
 * @param isFocus edittext has focused
 *
 * @return resource background color for edittext
 */
private fun getBackgroundColorEditText(
    validationState: ValidationValueState,
    isFocus: Boolean
): Int {
    return if (!isFocus) {
        when (validationState) {
            ValidationValueState.Validate -> R.drawable.background_edit_text
            else -> R.drawable.background_edittext_error
        }
    } else {
        when (validationState) {
            ValidationValueState.Validate -> R.drawable.background_edittext_focus
            ValidationValueState.Empty,
            ValidationValueState.Invalidate -> R.drawable.background_edittext_error
            else -> R.drawable.background_edit_text
        }
    }
}