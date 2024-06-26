package com.superman.movieticket.ui.profile.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.User
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.infrastructure.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class UpdateActivityViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    val _apiLoading = MutableStateFlow(ApiState.NONE)
    val apiLoading = _apiLoading.asStateFlow()
    val _userinfo = MutableStateFlow<User?>(null)
    val userinfo = _userinfo.asStateFlow()

    init {
        HandleGetUserDetail()
    }

    fun HandleGetUserDetail() {
        viewModelScope.launch {
            _apiLoading.value = ApiState.LOADING
            authService.HandleGetUserDetail().enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    _userinfo.value = response?.body()
                    Log.d("Chinh", response?.body().toString())
                    _apiLoading.value = ApiState.SUCCESS
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    _apiLoading.value = ApiState.FAIL
                    Log.d(
                        "Chinh fail get accoount detail",
                        t.toString()
                    )
                }
            })
            _apiLoading.value = ApiState.NONE
        }
    }

    fun HandleUpdateUseInfo (fullName: String, birthday: String, userGender: String, avatar: File?) {

        val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthdayDate: Date? = inputDateFormat.parse(birthday)
        val birthdayFormat: String = birthdayDate?.let { outputDateFormat.format(it) } ?: ""



        val _fullname = RequestBody.create("text/plain".toMediaTypeOrNull(), fullName)
        val _birthDate = RequestBody.create("text/plain".toMediaTypeOrNull(), birthdayFormat)
        val _userGender = RequestBody.create("text/plain".toMediaTypeOrNull(), "Male")


    }

}