package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ScreeningService {
    @GET("/client-api/Screening")
    fun HandleGetScreeningsAsync(@Header("x-query") xQuery: String): Call<SuccessResponse<ListResponse<Screening>>>
}