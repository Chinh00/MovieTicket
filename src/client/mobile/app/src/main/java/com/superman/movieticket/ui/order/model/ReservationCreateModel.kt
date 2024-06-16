package com.superman.movieticket.ui.order.model

import com.superman.movieticket.domain.entities.Screening

data class ReservationCreateModel(
    var screeningId: Screening,
    var seatReservations: List<SeatReservationsCreateModel>,
    var serviceReservations: List<ServiceReservationsCreateModel>
)
