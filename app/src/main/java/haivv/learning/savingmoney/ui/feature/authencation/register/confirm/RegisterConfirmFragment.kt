package haivv.learning.savingmoney.ui.feature.authencation.register.confirm

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import haivv.learning.base.BaseFragment
import haivv.learning.data.Result
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.databinding.RegistrationConfirmFragmentBinding
import haivv.learning.savingmoney.ui.dialog.BottomSheetMessageDialog
import haivv.learning.savingmoney.ui.feature.authencation.register.container.RegistrationContainerVM
import haivv.learning.savingmoney.utils.ValidationValueState

class RegisterConfirmFragment :
    BaseFragment<RegistrationConfirmFragmentBinding, RegistrationConfirmVM>() {
    private val viewModel: RegistrationConfirmVM by viewModels({ requireParentFragment() })
    private val containerVM: RegistrationContainerVM by viewModels({ requireParentFragment() })

    override fun initView() {
        viewBinding.run {
            lifecycleOwner = parentFragment
            dataViewModel = viewModel
            containerViewModel = containerVM
        }
        viewModel.enableRegister()
    }

    override fun attachInjectFragment() {}

    override fun initAction() {
        viewBinding.run {

            // handle validate password text change
            edtPassword.doAfterTextChanged { password ->
                viewModel.checkValidationPass(password.toString())
                viewModel.checkValidationPassConfirm(
                    viewBinding.edtConfirmPassword.text.toString()
                )
            }

            // handle validate confirm password text change
            edtConfirmPassword.doAfterTextChanged { passwordConfirm ->
                viewModel.checkValidationPassConfirm(
                    passwordConfirm.toString()
                )
            }

            // register user
            btnRegister.setOnClickListener {
                val user = containerVM.user.value
                if (user != null) {
                    user.password = edtPassword.text.toString()
                    containerViewModel?.register(user)
                }
            }
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.registration_confirm_fragment
    }

    override fun observeLiveData() {
        // observer state validation password
        viewModel.validatePassword.observe(this) { pass ->
            when (pass) {
                ValidationValueState.Validate -> viewModel.passwordError.set("")
                ValidationValueState.Invalidate -> viewModel.passwordError.set(getString(R.string.message_invalidate_password))
                ValidationValueState.Empty -> viewModel.passwordError.set(getString(R.string.message_require_password))
                ValidationValueState.LengthInvalidate -> viewModel.passwordError.set(getString(R.string.message_invalidate_length_password))
            }
        }

        // observer state validation confirm password
        viewModel.validatePasswordConfirm.observe(this) { passConfirm ->
            when (passConfirm) {
                ValidationValueState.Validate -> viewModel.passwordConfirmError.set("")
                ValidationValueState.Invalidate -> viewModel.passwordConfirmError.set(getString(R.string.message_invalidate_password_confirm))
                ValidationValueState.Empty -> viewModel.passwordConfirmError.set(getString(R.string.message_require_password_confirm))
                else -> {
                }
            }
        }

        // observer validation input info
        viewModel.isEnableRegister.observe(this) { passwordValidation ->
            viewModel.isEnableRegisterBtn.set((passwordValidation.validatePassword && passwordValidation.validatePassConfirm))
        }

        // observer state register user
        containerVM.registerValue.observe(this) { registerValue ->
            handleRegisterState(registerValue)
        }
    }

    /**
     *  Handing state register result
     *
     *  @param registerValue state register result
     */
    private fun handleRegisterState(registerValue: Result<Long>?) {
        val onSuccessListener = {
            popBackStack()
        }
        val onErrorListener = {
            popBackStack()
        }
        when (registerValue) {
            is Result.Success -> {
                viewBinding.progressBar.visibility = View.GONE
                BottomSheetMessageDialog.newInstance(
                    "Success!",
                    "Account has been created.",
                    onSuccessListener
                ).show(parentFragmentManager, "Success")
            }
            is Result.Error -> {
                viewBinding.progressBar.visibility = View.GONE
                BottomSheetMessageDialog.newInstance(
                    "Whoops!",
                    "Creating account failure.",
                    onErrorListener
                ).show(parentFragmentManager, "Error")
            }
            Result.Loading -> {
                viewBinding.progressBar.visibility = View.VISIBLE
            }
        }
    }
}