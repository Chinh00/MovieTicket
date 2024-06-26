package com.superman.movieticket.domain.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SeatPlaceService {

    @GET("/client-api/SeatPlace/screening/{screeningId}/room/{roomId}/seat/{seatId}")
    fun HandleGetSeatState(@Path("screeningId") screeningId: String,@Path("roomId") roomId: String, @Path("seatId") seatId: String): Call<SeatState>

    @POST("/client-api/SeatPlace/lock")
    fun HandleLockSeatState(@Body model: SeatStateCreateModel): Call<String>


    @POST("/client-api/SeatPlace/unlock")
    fun HandleUnLockSeatState(@Query("lockId") lockId: String): Call<SeatState>


}
data class SeatState (
    var id: String,
    var roomId: String,
    var screeningId: String,
    var seatId: String
)
data class SeatStateCreateModel (
    var roomId: String,
    var screeningId: String,
    var seatId: String
)
