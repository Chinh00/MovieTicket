package com.superman.movieticket.ui.order.model

import com.superman.movieticket.domain.entities.Screening

data class ReservationCreateModel(
    var screeningId: String,
    var seatReservations: MutableList<SeatReservationsCreateModel> = mutableListOf(),
    var serviceReservations: MutableList<ServiceReservationsCreateModel> = mutableListOf(),
    var transactionId: String = "",
    var reservationState: Int = 0
)


