package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationService {
    @GET("/client-api/Reservation")
    fun HandleGetReservationAsync(@Header("x-query") query: String): Call<SuccessResponse<ListResponse<Reservation>>>
    @GET("screening/{screeningId}/seats")
    suspend fun getReservedSeats(@Path("screeningId") screeningId: String): List<Reservation>
    @POST("/client-api/Reservation")
    fun HandleCreateReservationCreateAsync (@Body reservationCreateModel: ReservationCreateModel): Call<SuccessResponse<Reservation>>
}