package haivv.learning.savingmoney.ui.feature.authencation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.material.transition.MaterialSharedAxis
import haivv.learning.data.Result
import haivv.learning.data.local.entities.User
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.common.SavingMoneyApplication
import haivv.learning.savingmoney.databinding.LoginFragmentBinding
import haivv.learning.savingmoney.ui.feature.authencation.BaseFingerprint
import javax.inject.Inject

class LoginFragment : BaseFingerprint<LoginFragmentBinding, LoginViewModel>() {

    private lateinit var callbackManager: CallbackManager
    private val callback = object : BiometricPrompt.AuthenticationCallback() {

    }


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun getLayoutResourceId(): Int {
        return R.layout.login_fragment
    }

    override fun initView() {
        callbackManager = CallbackManager.Factory.create()

        viewBinding.btnLoginFace.run {
            setPermissions("email")
            fragment = this@LoginFragment
        }

        viewBinding.isSupportFingerprint = biometricUtils.supportedFingerprint(requireContext())

    }

    override fun initAction() {
        // navigate to register fragment
        viewBinding.tvRegister.setOnClickListener {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            val actionRegister = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            navigateSafety(actionRegister)
        }

        handleLoginFacebook()

        handleLoginFingerPrint()

        viewBinding.btnLogin.setOnClickListener {
            val user = User("")
            user.email = viewBinding.edtEmail.text.toString().trim()
            user.password = viewBinding.edtPassword.text.toString().trim()
            loginViewModel.login(user)
        }

    }

    private fun handleLoginFingerPrint() {
        viewBinding.imgFingerprint.setOnClickListener {
            handleFingerprint()
        }
    }

    private fun handleFingerprint() {
        if (biometricUtils.enrolledFingerPrint(requireContext())) {
            showBiometricPrompt()
        } else {
            enableSettingFingerprint()
        }
    }

    private fun showBiometricPrompt(
    ) {
        if (!cryptographyManager.detectedChangeFingerprint(secretKeyName)) {
            val cipher = cryptographyManager.getInitializedCipherForEncryption(
                secretKeyName
            )
            createBiometricPrompt(cipher, callback)
        } else {
            // handle detected change fingerprint.
        }
    }

    private fun handleLoginFacebook() {
        viewBinding.btnLoginFace.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {

                    if (result?.accessToken != null) {
                        getUserProfile(result.accessToken)
                    }
                }

                override fun onCancel() {
                    Log.d("User info:", "Login facebook canceled.")
                }

                override fun onError(error: FacebookException?) {
                    Log.d("User info:", error?.message + "")
                }

            })
    }

    private fun getUserProfile(currentAccessToken: AccessToken) {
        val graphRequest = GraphRequest.newMeRequest(currentAccessToken) { userInfo, response ->
            Log.d("User info:", userInfo.toString())
        }

        val bundle = Bundle()
        bundle.putString("fields", "first_name,last_name,email,id")
        graphRequest.parameters = bundle
        graphRequest.executeAsync()
    }

    override fun attachInjectFragment() {
        (requireActivity().application as SavingMoneyApplication).savingMoneyComponent.authenticationComponent()
            .create()
            .injectLoginFragment(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun observeLiveData() {
        loginViewModel.loginValue.observe(this, { result ->
            when (result) {
                is Result.Loading -> {
                    Log.d("haivv", "Loading")
                }
                is Result.Success -> {
                    Log.d("haivv", "Success")
                    val user: User? = result.data
                    user?.let { Log.d("haivv", user.toString()) } ?: Log.d("haivv", "null")
                }
                is Result.Error -> {
                    Log.d("haivv", "Error")
                }
            }
        })
    }


}