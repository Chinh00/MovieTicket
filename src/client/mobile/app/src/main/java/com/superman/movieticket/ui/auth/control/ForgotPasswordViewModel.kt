package com.superman.movieticket.ui.auth.control

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    private val _email = MutableStateFlow("")

    val email: StateFlow<String?> get() = _email

    private val _errorE = MutableStateFlow("")

    val errorE: StateFlow<String> get() = _errorE
    fun setEmail(s: String) {
        _email.value = s
    }

    fun setErrorEmail(s: String) {
        _errorE.value = s
    }

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> get() = _isFormValid

    fun validateForm() {
        CoroutineScope(Dispatchers.Default).launch {
            _errorE.value=(if (!validateEmail()) "Invalid email" else "")

            _isFormValid.value=(_errorE.value == "")
        }
    }

    private fun validateEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }
}