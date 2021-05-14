package haivv.learning.savingmoney.utils

sealed class ValidationValueState {

    object Validate : ValidationValueState()

    object Invalidate : ValidationValueState()

    object Empty : ValidationValueState()

    object LengthInvalidate : ValidationValueState()
}

inline fun ValidationValueState.handleValidation(
    handleValidate: () -> Unit,
    handleInvalidate: () -> Unit,
    handleEmpty: () -> Unit
) {
    when (this) {
        ValidationValueState.Validate -> handleValidate()
        ValidationValueState.Invalidate -> handleInvalidate()
        ValidationValueState.Empty -> handleEmpty()
    }
}
