package haivv.learning.savingmoney.ui.feature.authencation.register.confirm

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import haivv.learning.base.BaseViewModel
import haivv.learning.savingmoney.common.REGEX_PASSWORD
import haivv.learning.savingmoney.utils.ValidationValueState
import haivv.learning.savingmoney.utils.handleValidation
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Saving data using in Confirm form
 */
class RegistrationConfirmVM @Inject constructor() : BaseViewModel() {
    val passwordError = ObservableField<String>()
    val passwordConfirmError = ObservableField<String>()
    val isEnableRegisterBtn = ObservableField<Boolean>()

    private val _validatePassword: MutableLiveData<ValidationValueState> = MutableLiveData()
    val validatePassword: LiveData<ValidationValueState>
        get() = _validatePassword

    private val _passWord: MutableLiveData<String> = MutableLiveData()

    private val _validatePasswordConfirm: MutableLiveData<ValidationValueState> = MutableLiveData()
    val validatePasswordConfirm: LiveData<ValidationValueState>
        get() = _validatePasswordConfirm

    private val _isEnableRegister: MediatorLiveData<PasswordValidation> = MediatorLiveData()
    val isEnableRegister: LiveData<PasswordValidation>
        get() = _isEnableRegister

    init {
        compositeDisposable = CompositeDisposable()
    }

    /**
     *  Validation password input
     *
     *  @param pass password
     */
    fun checkValidationPass(pass: String) {
        _passWord.value = pass
        when {
            TextUtils.isEmpty(pass) -> {
                _validatePassword.value =
                    ValidationValueState.Empty
            }
            REGEX_PASSWORD.toRegex().matches(pass) -> {
                _validatePassword.value = ValidationValueState.Validate
            }
            pass.length < 8 -> {
                _validatePassword.value =
                    ValidationValueState.LengthInvalidate
            }
            else -> {
                _validatePassword.value =
                    ValidationValueState.Invalidate
            }
        }
    }

    /**
     * Validation password confirm
     *
     * @param passConfirm password confirm
     */
    fun checkValidationPassConfirm(passConfirm: String) {
        when {
            TextUtils.isEmpty(passConfirm) -> {
                _validatePasswordConfirm.value =
                    ValidationValueState.Empty
            }
            passConfirm == _passWord.value -> {
                _validatePasswordConfirm.value = ValidationValueState.Validate
            }
            else -> {
                _validatePasswordConfirm.value =
                    ValidationValueState.Invalidate
            }
        }
    }

    // Check validation input info
    fun enableRegister() {
        _isEnableRegister.value = PasswordValidation()
        addSourceValidatePass()
        addSourceValidateConfirmPass()
    }

    /**
     * Add live data validate Password to check validation register
     */
    private fun addSourceValidatePass() {
        _isEnableRegister.addSource(validatePassword) { validatePassword ->
            val passwordValidation = _isEnableRegister.value
            val handleEmpty = {
                passwordValidation?.validatePassword = false
                _isEnableRegister.value = passwordValidation
            }
            val handleValidate = {
                passwordValidation?.validatePassword = true
                _isEnableRegister.value = passwordValidation
            }
            val handleInvalidate = {
                passwordValidation?.validatePassword = false
                _isEnableRegister.value = passwordValidation
            }
            validatePassword.handleValidation(handleValidate, handleInvalidate, handleEmpty)
        }
    }

    /**
     * Add live data validate confirm password to check validation register
     */
    private fun addSourceValidateConfirmPass() {
        _isEnableRegister.addSource(validatePasswordConfirm) { validatePassConfirm ->
            val passwordValidation = _isEnableRegister.value
            val handleEmpty = {
                passwordValidation?.validatePassConfirm = false
                _isEnableRegister.value = passwordValidation
            }
            val handleValidate = {
                passwordValidation?.validatePassConfirm = true
                _isEnableRegister.value = passwordValidation
            }
            val handleInvalidate = {
                passwordValidation?.validatePassConfirm = false
                _isEnableRegister.value = passwordValidation
            }
            validatePassConfirm.handleValidation(handleValidate, handleInvalidate, handleEmpty)
        }
    }
}