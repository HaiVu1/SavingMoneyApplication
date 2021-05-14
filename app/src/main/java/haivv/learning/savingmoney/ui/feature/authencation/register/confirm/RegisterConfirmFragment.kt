package haivv.learning.savingmoney.ui.feature.authencation.register.confirm

import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import haivv.learning.base.BaseFragment
import haivv.learning.data.Result
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.databinding.RegistrationConfirmFragmentBinding
import haivv.learning.savingmoney.ui.feature.authencation.register.container.RegistrationContainerVM
import haivv.learning.savingmoney.utils.ValidationValueState

class RegisterConfirmFragment :
    BaseFragment<RegistrationConfirmFragmentBinding, RegistrationConfirmVM>() {
    private val viewModel: RegistrationConfirmVM by viewModels ({requireParentFragment() })
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

            edtPassword.doAfterTextChanged { password ->
                viewModel.checkValidationPass(password.toString())
                viewModel.checkValidationPassConfirm(
                    viewBinding.edtConfirmPassword.text.toString()
                )
            }

            edtConfirmPassword.doAfterTextChanged { passwordConfirm ->
                viewModel.checkValidationPassConfirm(
                    passwordConfirm.toString()
                )
            }

            btnRegister.setOnClickListener {
                val user = containerVM.user.value
               if(user != null){
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
        viewModel.validatePassword.observe(this) { pass ->
            when (pass) {
                ValidationValueState.Validate -> viewModel.passwordError.set("")
                ValidationValueState.Invalidate -> viewModel.passwordError.set(getString(R.string.message_invalidate_password))
                ValidationValueState.Empty -> viewModel.passwordError.set(getString(R.string.message_require_password))
                ValidationValueState.LengthInvalidate ->  viewModel.passwordError.set(getString(R.string.message_invalidate_length_password))
            }
        }

        viewModel.validatePasswordConfirm.observe(this) { passConfirm ->
            when (passConfirm) {
                ValidationValueState.Validate -> viewModel.passwordConfirmError.set("")
                ValidationValueState.Invalidate -> viewModel.passwordConfirmError.set(getString(R.string.message_invalidate_password_confirm))
                ValidationValueState.Empty -> viewModel.passwordConfirmError.set(getString(R.string.message_require_password_confirm))
                else -> {}
            }
        }

        viewModel.isEnableRegister.observe(this) { passwordValidation ->
            viewModel.isEnableRegisterBtn.set((passwordValidation.validatePassword && passwordValidation.validatePassConfirm))
        }

        containerVM.registerValue.observe(this){registerValue ->
            when(registerValue){
                is Result.Success -> {
                    Log.d("Register","Success")
                }
                is Result.Error -> {
                    Log.d("Register","Error")
                }
                Result.Loading -> {
                    Log.d("Register","Loading")
                }
            }
        }
    }
}