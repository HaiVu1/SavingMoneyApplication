package haivv.learning.savingmoney.biometric

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import haivv.learning.savingmoney.R


class BiometricUtils {
    private val TAG = "BiometricPromptUtils"
    fun createBiometricPrompt(
        fragmentActivity: FragmentActivity,
        authenticationCallback: BiometricPrompt.AuthenticationCallback
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(fragmentActivity)

        return BiometricPrompt(fragmentActivity, executor, authenticationCallback)
    }

    fun createPromptInfo(fragmentActivity: FragmentActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(fragmentActivity.getString(R.string.prompt_info_title))
            setSubtitle(fragmentActivity.getString(R.string.prompt_info_subtitle))
            setDescription(fragmentActivity.getString(R.string.prompt_info_description))
            setConfirmationRequired(false)
            setNegativeButtonText(fragmentActivity.getString(R.string.prompt_info_use_app_password))
        }.build()

    fun supportedFingerprint(context: Context): Boolean {
        val value = BiometricManager.from(context).canAuthenticate()
        return value != BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
    }

    fun enrolledFingerPrint(context: Context): Boolean{
        val value = BiometricManager.from(context).canAuthenticate()
        return value != BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
    }
}
