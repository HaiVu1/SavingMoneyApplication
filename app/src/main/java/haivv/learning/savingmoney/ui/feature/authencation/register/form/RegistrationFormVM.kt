package haivv.learning.savingmoney.ui.feature.authencation.register.form

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import haivv.learning.base.BaseViewModel
import haivv.learning.savingmoney.common.REGEX_VN_PHONE
import haivv.learning.savingmoney.utils.ValidationValueState
import haivv.learning.savingmoney.utils.handleValidation
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RegistrationFormVM @Inject constructor() : BaseViewModel() {
    private val _validateFirstNameState: MutableLiveData<ValidationValueState> = MutableLiveData()
    val validateFirstNameState: LiveData<ValidationValueState>
        get() = _validateFirstNameState

    private val _validateLastName: MutableLiveData<ValidationValueState> = MutableLiveData()
    val validateLastName: LiveData<ValidationValueState>
        get() = _validateLastName

    private val _validateEmail: MutableLiveData<ValidationValueState> = MutableLiveData()
    val validateEmail: LiveData<ValidationValueState>
        get() = _validateEmail

    private val _validatePhone: MutableLiveData<ValidationValueState> = MutableLiveData()
    val validatePhone: LiveData<ValidationValueState>
        get() = _validatePhone

    private val _isEnableNextPage: MediatorLiveData<RegistrationValidation>
    val isEnableNextPage: LiveData<RegistrationValidation>
        get() = _isEnableNextPage

    init {
        compositeDisposable = CompositeDisposable()
        _isEnableNextPage = MediatorLiveData()
    }

    val firstNameError = ObservableField<String>()
    val lastNameError = ObservableField<String>()
    val emailError = ObservableField<String>()
    val phoneError = ObservableField<String>()
    val isEnableNextBtn = ObservableField<Boolean>()

    /**
     * set state validation first name
     *
     * @param firstName first name
     */
    fun validateFirstName(firstName: String) {
        if (TextUtils.isEmpty(firstName)) {
            _validateFirstNameState.value = ValidationValueState.Empty
        } else {
            _validateFirstNameState.value = ValidationValueState.Validate
        }
    }

    /**
     * set state validation last name
     *
     * @param lastName last name
     */
    fun validateLastName(lastName: String) {
        when {
            TextUtils.isEmpty(lastName) -> {
                _validateLastName.value = ValidationValueState.Empty
            }
            else -> {
                _validateLastName.value = ValidationValueState.Validate
            }
        }
    }

    /**
     * set state validation email
     *
     * @param email email
     */
    fun validateEmail(email: String) {
        when {
            TextUtils.isEmpty(email) -> {
                _validateEmail.value =
                    ValidationValueState.Empty
            }
            Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _validateEmail.value = ValidationValueState.Validate
            }
            else -> {
                _validateEmail.value =
                    ValidationValueState.Invalidate
            }
        }
    }

    /**
     * set state validation phone
     *
     * @param phone phone number
     */
    fun validatePhone(phone: String) {
        when {
            TextUtils.isEmpty(phone) -> {
                _validatePhone.value = ValidationValueState.Empty
            }
            REGEX_VN_PHONE.toRegex().matches(phone) -> {
                _validatePhone.value = ValidationValueState.Validate
            }
            else -> {
                _validatePhone.value = ValidationValueState.Invalidate
            }
        }
    }

    // validation info in page
    fun enableNextPage() {
        _isEnableNextPage.value = RegistrationValidation()
        addSourceValidateFirstName()
        addSourceValidationLastName()
        addSourceValidationPhone()
        addSourceValidationEmail()
    }

    /**
     * Add live data validate first name to check validation go next page
     */
    private fun addSourceValidateFirstName() {
        _isEnableNextPage.addSource(validateFirstNameState) { validateFirstName ->
            val registrationValidation = _isEnableNextPage.value
            val handleEmpty = {
                registrationValidation?.validateFirstName = false
                _isEnableNextPage.value = registrationValidation
            }
            val handleInvalidate = {
                registrationValidation?.validateFirstName = false
                _isEnableNextPage.value = registrationValidation
            }
            val handleValidate = {
                registrationValidation?.validateFirstName = true
                _isEnableNextPage.value = registrationValidation
            }
            validateFirstName.handleValidation(handleValidate, handleInvalidate, handleEmpty)
        }
    }

    /**
     * Add live data validate last name to check validation go next page
     */
    private fun addSourceValidationLastName() {
        _isEnableNextPage.addSource(validateLastName) { validateLastName ->
            val registrationValidation = _isEnableNextPage.value
            val handleEmpty = {
                registrationValidation?.validateLastName = false
                _isEnableNextPage.value = registrationValidation
            }
            val handleInvalidate = {
                registrationValidation?.validateLastName = false
                _isEnableNextPage.value = registrationValidation
            }
            val handleValidate = {
                registrationValidation?.validateLastName = true
                _isEnableNextPage.value = registrationValidation
            }
            validateLastName.handleValidation(handleValidate, handleInvalidate, handleEmpty)
        }
    }

    /**
     * Add live data validate phone number to check validation go next page
     */
    private fun addSourceValidationPhone() {
        _isEnableNextPage.addSource(validatePhone) { validatePhone ->
            val registrationValidation = _isEnableNextPage.value
            val handleEmpty = {
                registrationValidation?.validatePhone = false
                _isEnableNextPage.value = registrationValidation
            }
            val handleValidate = {
                registrationValidation?.validatePhone = true
                _isEnableNextPage.value = registrationValidation
            }
            val handleInvalidate = {
                registrationValidation?.validatePhone = false
                _isEnableNextPage.value = registrationValidation
            }
            validatePhone.handleValidation(handleValidate, handleInvalidate, handleEmpty)
        }
    }


    /**
     * Add live data validate email to check validation go next page
     */
    private fun addSourceValidationEmail() {
        _isEnableNextPage.addSource(validateEmail) { validateEmail ->
            val registrationValidation = _isEnableNextPage.value
            val handleEmpty = {
                registrationValidation?.validateEmail = false
                _isEnableNextPage.value = registrationValidation
            }
            val handleValidate = {
                registrationValidation?.validateEmail = true
                _isEnableNextPage.value = registrationValidation
            }
            val handleInvalidate = {
                registrationValidation?.validateEmail = false
                _isEnableNextPage.value = registrationValidation
            }
            validateEmail.handleValidation(handleValidate, handleInvalidate, handleEmpty)
        }
    }
}