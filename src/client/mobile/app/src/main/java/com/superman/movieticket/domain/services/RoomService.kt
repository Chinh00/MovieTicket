package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Room
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RoomService {
    @GET("/client-api/Room")
    fun HandleGetRoomsAsync(@Header("x-query") query: String): Call<SuccessResponse<ListResponse<Room>>>
    @GET("/client-api/Room/{id}/Screening/{screeningId}")
    fun HandleGetRoomByIdAsync(@Path("id") id: String, @Path("screeningId") screeningId: String): Call<SuccessResponse<Room>>

}

