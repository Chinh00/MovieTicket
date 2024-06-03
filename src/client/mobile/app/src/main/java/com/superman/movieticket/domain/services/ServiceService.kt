package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ServiceService {
    @GET("/client-api/Service")
    fun HandleGetServicesAsync(@Header("x-query") xQuery: String): Call<SuccessResponse<ListResponse<Screening>>>
}