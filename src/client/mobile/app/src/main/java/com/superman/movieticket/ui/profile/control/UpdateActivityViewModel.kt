package com.superman.movieticket.ui.profile.control

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.superman.movieticket.domain.entities.User
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.domain.services.UserUpdateInfoModel
import com.superman.movieticket.infrastructure.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
                    _apiLoading.value = ApiState.LOADING
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

    @SuppressLint("NewApi")
    fun HandleUpdateUseInfo (fullName: String, birthday: String, userGender: Int, avatar: String?) {
        _apiLoading.value = ApiState.LOADING

            // Định nghĩa định dạng của chuỗi vào và chuỗi ra
            val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

            // Parse chuỗi vào thành LocalDate
            val localDate = LocalDate.parse(birthday, inputFormatter)

            // Thêm giờ phút giây mặc định (00:00:00) để tạo thành LocalDateTime
            val localDateTime = LocalDateTime.of(localDate, LocalDateTime.now().toLocalTime())

            // Định dạng lại thành chuỗi theo định dạng mong muốn
            val formattedDateTime = localDateTime.format(outputFormatter)
        Log.d("Chinh", formattedDateTime.toString())



        authService.UpdateUserInfo(UserUpdateInfoModel(fullName,
            formattedDateTime.toString(), avatar?:"", userGender)).enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful) {
                    val updatedUser = response.body()
                    _apiLoading.value = ApiState.SUCCESS
                    // Handle the successful response, for example:
                    Log.d("UpdateUser", "User updated: $updatedUser")
                } else {
                    Log.e("UpdateUser", "Failed to update user: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Update user fail", t.toString())
                _apiLoading.value = ApiState.FAIL
            }

        })
        _apiLoading.value = ApiState.NONE
    }


}