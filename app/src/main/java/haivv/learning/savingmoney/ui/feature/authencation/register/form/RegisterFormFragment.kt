package haivv.learning.savingmoney.ui.feature.authencation.register.form

import android.widget.RadioGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import haivv.learning.base.BaseFragment
import haivv.learning.data.local.entities.User
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.databinding.RegistrationFromFragmentBinding
import haivv.learning.savingmoney.ui.datetime.DatePickerFragment
import haivv.learning.savingmoney.ui.feature.authencation.register.container.RegistrationContainerVM
import haivv.learning.savingmoney.utils.ValidationValueState
import haivv.learning.savingmoney.utils.binding.handleFocusChange
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
            // handle validate first name
            edtFirstName.doAfterTextChanged {
                viewModel.validateFirstName(it.toString())
            }
            handleFocusChange(
                edtFirstName,
                viewModel.validateFirstNameState,
                this@RegisterFormFragment
            )

            // handle validation last name
            edtLastName.doAfterTextChanged {
                viewModel.validateLastName(it.toString())
            }
            handleFocusChange(edtLastName, viewModel.validateLastName, this@RegisterFormFragment)

            // handle validation email
            edtEmail.doAfterTextChanged {
                viewModel.validateEmail(it.toString())
            }
            handleFocusChange(
                edtEmail,
                viewModel.validateEmail,
                this@RegisterFormFragment
            )

            // handle validation phone
            edtPhone.doAfterTextChanged {
                viewModel.validatePhone(it.toString())
            }
            handleFocusChange(
                edtPhone,
                viewModel.validatePhone,
                this@RegisterFormFragment
            )

            // handle validation dob
            edtDOB.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(childFragmentManager, "datePicker")
                datePickerFragment.setDateInView = { date ->
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

    /**
     * Show message state validation last name
     *
     * @param validateLastName state validation last name
     */
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

    /**
     * Show message state validation email
     *
     * @param validateEmail state validation email
     */
    private fun showValidationEmail(validateEmail: ValidationValueState) {
        when (validateEmail) {
            ValidationValueState.Validate -> viewModel.emailError.set("")
            ValidationValueState.Invalidate -> viewModel.emailError.set(getString(R.string.message_invalidate_email))
            ValidationValueState.Empty -> viewModel.emailError.set(getString(R.string.message_require_email))
            else -> {
            }
        }
    }

    /**
     * Show message state validation phone
     *
     * @param validatePhone state validation phone
     */
    private fun showValidationPhone(validatePhone: ValidationValueState) {
        when (validatePhone) {
            ValidationValueState.Validate -> viewModel.phoneError.set("")
            ValidationValueState.Invalidate -> viewModel.phoneError.set(getString(R.string.message_invalidate_phone))
            ValidationValueState.Empty -> viewModel.phoneError.set(getString(R.string.message_require_phone))
            else -> {
            }
        }
    }


    /**
     * Show message state validation next page
     *
     * @param enableNextPage state validation next page
     */
    private fun enableBtnNext(enableNextPage: RegistrationValidation) {
        viewModel.isEnableNextBtn.set(
            (enableNextPage.validateFirstName &&
                    enableNextPage.validateLastName &&
                    enableNextPage.validateEmail &&
                    enableNextPage.validatePhone)
        )
    }

    /**
     * Saving data in current page then show next page
     */
    private fun showConfirmPage() {
        viewBinding.btnNext.setOnClickListener {
            val user = User(
                userId = UUID.randomUUID().toString(),
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