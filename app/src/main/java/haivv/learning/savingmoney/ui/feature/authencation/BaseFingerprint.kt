package haivv.learning.savingmoney.ui.feature.authencation

import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricPrompt
import androidx.databinding.ViewDataBinding
import haivv.learning.base.BaseFragment
import haivv.learning.base.BaseViewModel
import haivv.learning.savingmoney.biometric.BiometricUtils
import haivv.learning.savingmoney.biometric.CryptographyManagerImpl
import javax.crypto.Cipher

abstract class BaseFingerprint<V : ViewDataBinding, VM : BaseViewModel> : BaseFragment<V, VM>() {
    protected val biometricUtils = BiometricUtils()
    protected val cryptographyManager = CryptographyManagerImpl()
    protected val secretKeyName = "SAVING_MONEY_FP"

    protected fun enableSettingFingerprint() {
        startActivity( Intent(Settings.ACTION_SECURITY_SETTINGS))
    }

//    private fun showBiometricPromptForSetting() {
//        val cipher = cryptographyManager.getInitializedCipher(
//            secretKeyName
//        )
//        createBiometricPrompt(cipher)
//    }


    protected fun createBiometricPrompt(
        cipher: Cipher,
        callback: BiometricPrompt.AuthenticationCallback
    ) {
        val biometricPrompt: BiometricPrompt = biometricUtils.createBiometricPrompt(
            fragmentActivity = requireActivity(),
            callback
        )

        val promptInfo = biometricUtils.createPromptInfo(requireActivity())
        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }
}