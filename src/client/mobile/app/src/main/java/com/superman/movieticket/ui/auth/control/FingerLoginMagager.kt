package com.superman.movieticket.ui.auth.control

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.concurrent.Executor

class FingerLoginMagager(private val activity: AppCompatActivity) {

    private val _authResult = Channel<BiometricState>()
    val authResult = _authResult.receiveAsFlow()

    private lateinit var biometricPrompt: BiometricPrompt
    private val executor: Executor = ContextCompat.getMainExecutor(activity)


    fun showBiometricPrompt(title: String, description: String) {
        val biometricManager = BiometricManager.from(activity)
        val promptInfo = PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Authenciate")

        val authenticators = if (Build.VERSION.SDK_INT >= 30) {
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL

        } else {
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        }
        if(Build.VERSION.SDK_INT<30){
            promptInfo.setNegativeButtonText("Cancel")
        }
        when(biometricManager.canAuthenticate(authenticators)){
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                _authResult.trySend(BiometricState.HardwareUnavailable)
                return
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                _authResult.trySend(BiometricState.FeatureUnavailable)
                return

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                _authResult.trySend(BiometricState.AuthencationNotSet)
                return

            }
            else->Unit

        }
        val prompt = BiometricPrompt(activity,object :BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                _authResult.trySend(BiometricState.Error(errString.toString()))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                _authResult.trySend(BiometricState.Success)

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                _authResult.trySend(BiometricState.Failed)

            }

        })
        prompt.authenticate(promptInfo.build())
    }

    sealed interface BiometricState {

        data object HardwareUnavailable : BiometricState
        data object FeatureUnavailable : BiometricState
        data class Error(val message: String) : BiometricState
        data object Success : BiometricState
        data object Failed : BiometricState
        data object AuthencationNotSet : BiometricState



    }
}



