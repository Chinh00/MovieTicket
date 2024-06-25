package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class Reservation (
    var screeningId: String,
    var totalPrice: Long,
    var itemPrice: Long,
    var screening: Screening,
    var seatReservations: MutableList<SeatReservation>,
    var serviceReservations: MutableList<ServiceReservation>
) : BaseEntity()
