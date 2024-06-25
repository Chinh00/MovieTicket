package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class SeatReservation (
    var seatId: String,
    var seat: Seat?,
    var reservationId: String,
    var reservation: Reservation?
) : BaseEntity()