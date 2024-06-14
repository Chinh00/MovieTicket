package com.superman.movieticket.domain.services

import retrofit2.http.POST

interface DeviceService {
    @POST("/")
    fun HandleRegisterDevice();
}