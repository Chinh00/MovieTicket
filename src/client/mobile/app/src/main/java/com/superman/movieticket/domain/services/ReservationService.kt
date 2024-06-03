package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ReservationService {
    @GET("/client-api/Reservation")
    fun HandleGetReservationAsync(@Header("x-query") query: String): Call<SuccessResponse<ListResponse<Reservation>>>


}