package haivv.learning.savingmoney.ui.feature.authencation.register.form

import android.app.Dialog
import android.os.Bundle
import android.widget.RadioGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import haivv.learning.base.BaseFragment
import haivv.learning.data.local.entities.User
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.databinding.RegistrationFromFragmentBinding
import haivv.learning.savingmoney.ui.datetime.DatePickerFragment
import haivv.learning.savingmoney.ui.datetime.createDatePickerMaxDate
import haivv.learning.savingmoney.ui.feature.authencation.register.container.RegistrationContainerVM
import haivv.learning.savingmoney.utils.ValidationValueState
import java.util.*

class RegisterFormFragment :
    BaseFragment<RegistrationFromFragmentBinding, RegistrationFormVM>() {
    private val viewModel: RegistrationFormVM by viewModels({ requireParentFragment() })
    private val containerVMContainer: RegistrationContainerVM by viewModels({ requireParentFragment() })

    override fun getLayoutResourceId(): Int {
        return R.layout.registration_from_fragment
    }

    override fun initView() {
        viewBinding.run {
            lifecycleOwner = parentFragment
            dataViewModel = viewModel
            radioGroupGender.check(R.id.rbFemale)
        }
        viewModel.enableNextPage()
    }

    override fun initAction() {
        viewBinding.run {
            edtFirstName.doAfterTextChanged {
                viewModel.validateFirstName(it.toString())
            }
            edtLastName.doAfterTextChanged {
                viewModel.validateLastName(it.toString())
            }
            edtEmail.doAfterTextChanged {
                viewModel.validateEmail(it.toString())
            }
            edtPhone.doAfterTextChanged {
                viewModel.validatePhone(it.toString())
            }

            edtDOB.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(childFragmentManager, "datePicker")
                datePickerFragment.setDateInView = {date->
                    edtDOB.text = date
                }
            }

        }
        showConfirmPage()
    }

    override fun observeLiveData() {
        viewModel.run {
            validateFirstNameState.observe(this@RegisterFormFragment, { validateFirstName ->
                showValidationFirstName(validateFirstName)
            })

            validateLastName.observe(this@RegisterFormFragment, { validateLastName ->
                showValidationLastName(validateLastName)
            })

            validatePhone.observe(this@RegisterFormFragment, { validatePhone ->
                showValidationPhone(validatePhone)
            })

            validateEmail.observe(this@RegisterFormFragment, { validateEmail ->
                showValidationEmail(validateEmail)
            })

            isEnableNextPage.observe(this@RegisterFormFragment, { isEnableNextPage ->
                enableBtnNext(isEnableNextPage)
            })
        }
    }

    override fun attachInjectFragment() {}

    private fun showValidationFirstName(it: ValidationValueState) {
        when (it) {
            ValidationValueState.Validate -> viewModel.firstNameError.set("")
            else -> viewModel.firstNameError.set(getString(R.string.message_require_first_name))
        }
    }

    private fun showValidationLastName(validateLastName: ValidationValueState) {
        when (validateLastName) {
            ValidationValueState.Validate -> {
                viewModel.lastNameError.set("")
            }
            else -> {
                viewModel.lastNameError.set(getString(R.string.message_require_last_name))
            }
        }
    }

    private fun handleGender(radioGroupGender: RadioGroup): Int {
        return when (radioGroupGender.checkedRadioButtonId) {
            R.id.rbFemale -> 0
            else -> 1
        }
    }

    private fun showValidationEmail(validateEmail: ValidationValueState) {
        when (validateEmail) {
            ValidationValueState.Validate -> viewModel.emailError.set("")
            ValidationValueState.Invalidate -> viewModel.emailError.set(getString(R.string.message_invalidate_email))
            ValidationValueState.Empty -> viewModel.emailError.set(getString(R.string.message_require_email))
        }
    }

    private fun showValidationPhone(validatePhone: ValidationValueState) {
        when (validatePhone) {
            ValidationValueState.Validate -> viewModel.phoneError.set("")
            ValidationValueState.Invalidate -> viewModel.phoneError.set(getString(R.string.message_invalidate_phone))
            ValidationValueState.Empty -> viewModel.phoneError.set(getString(R.string.message_require_phone))
        }
    }

    private fun enableBtnNext(enableNextPage: RegistrationValidation) {
        viewModel.isEnableNextBtn.set(
            (enableNextPage.validateFirstName &&
                    enableNextPage.validateLastName &&
                    enableNextPage.validateEmail &&
                    enableNextPage.validatePhone)
        )
    }

    private fun showConfirmPage() {
        viewBinding.btnNext.setOnClickListener {
            val user = User(
                userId = "2",
                userName = viewBinding.edtFirstName.text.toString().trim(),
                password = "",
                firstName = viewBinding.edtFirstName.text.toString().trim(),
                lastName = viewBinding.edtLastName.text.toString().trim(),
                email = viewBinding.edtEmail.text.toString().trim(),
                phoneNumber = viewBinding.edtPhone.text.toString().trim(),
                gender = handleGender(viewBinding.radioGroupGender),
                dob = viewBinding.edtDOB.text.toString().trim()
            )
            containerVMContainer.goNextPage()
            containerVMContainer.setUser(user)
        }
    }
}