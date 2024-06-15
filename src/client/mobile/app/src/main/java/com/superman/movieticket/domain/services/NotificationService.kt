package com.superman.movieticket.domain.services

import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("/notification-api/Device")
    fun RegisterDevice(@Body model: NotificationDeviceCreateModel): Call<SuccessResponse<String>>
}

data class NotificationDeviceCreateModel (
    val deviceId: String,
    val token: String,
    val userId: String
)
