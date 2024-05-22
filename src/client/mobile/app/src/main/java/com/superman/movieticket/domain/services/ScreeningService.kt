package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ScreeningService {
    @GET("/screening")
    fun HandleGetScreeningsAsync(): Call<Response<SuccessResponse<ListResponse<Screening>>>>
}