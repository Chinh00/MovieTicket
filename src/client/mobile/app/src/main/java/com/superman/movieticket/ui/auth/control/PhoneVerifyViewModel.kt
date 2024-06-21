package com.superman.movieticket.ui.auth.control

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.activity.ComponentActivity

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PhoneVerifyViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val _verificationId = MutableStateFlow("")
    val verificationId: StateFlow<String> get() = _verificationId
    fun setVerifyCode(code: String) {
        _verificationId.value = code
    }
    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> get() = _timeLeft
    // Hàm gửi OTP
    private val _otpValue = MutableStateFlow("")
    val otpValue: StateFlow<String> get() = _otpValue
    fun setOtpValue(otp: String) {
        _otpValue.value = otp
    }
    init {
    }
    fun sendOtp(phoneNumber: String, activity: Activity, onOtpSent: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("PhoneVerifyComp1",phoneNumber)

                val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(activity)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            onOtpSent(true, "")
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            e.printStackTrace()
                            onOtpSent(false, e.message ?: "Unknown error")
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                        ) {

                            this@PhoneVerifyViewModel._verificationId.value = verificationId
                            onOtpSent(true, verificationId)
                        }
                    })
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            } catch (e: Exception) {
                e.printStackTrace()
                onOtpSent(false, e.message ?: "Unknown error")
            }
        }
    }
    private var countdownJob: Job? = null // Job to hold the countdown coroutine

    fun startCountdown() {
        countdownJob = viewModelScope.launch {
            _timeLeft.value = 60L
            while (_timeLeft.value > 0) {
                delay(1000L)
                _timeLeft.value -= 1
            }
        }
    }

    fun stopCountdown() {
        countdownJob?.cancel() // Cancel the countdown coroutine job
    }

    // Hàm xác minh OTP
    fun verifyOtp(otp: String, onVerified: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("OtpViewModel", verificationId.value)
                Log.d("OtpViewModel", "otp: $otp")

                val credential = PhoneAuthProvider.getCredential(verificationId.value, otp)
                signInWithPhoneAuthCredential(credential) { success ->
                    Log.d("OtpViewModel", success.toString())

                    onVerified(success)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onVerified(false)
            }
        }
    }

    // Hàm đăng nhập bằng credential
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, onSuccess: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess(true)
                    } else {
                        task.exception?.printStackTrace()
                        onSuccess(false)
                    }
                }
        }
    }
}
