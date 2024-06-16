package com.superman.movieticket.ui.auth.control

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OtpViewModel : ViewModel() {
    private val _state = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    var otpValue = MutableStateFlow("")
    private set
    
    
    fun setOtpValue(s:String){
        otpValue.value=s
    }
    val state: StateFlow<ForgotPasswordState> = _state
    fun isCheckCode(){
        if(otpValue.value=="123456"){
            _state.value = ForgotPasswordState.Success
        }else{
            _state.value = ForgotPasswordState.Loading

        }
    }



}
sealed class ForgotPasswordState {
    object Idle : ForgotPasswordState()
    object Loading : ForgotPasswordState()
    object EmailSent : ForgotPasswordState()
    object Success : ForgotPasswordState()
    data class Error(val message: String) : ForgotPasswordState()
}