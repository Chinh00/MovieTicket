package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity
import java.io.Serializable
import java.util.Date

data class Reservation (
    var id :String,
    var screeningId: String,
    var totalPrice: Long,
    var itemPrice: Long,
    var screening: Screening,
    var seatReservations: MutableList<SeatReservation>,
    var serviceReservations: MutableList<ServiceReservation>,
    var createdDate : String= Date().toString()
) : Serializable
