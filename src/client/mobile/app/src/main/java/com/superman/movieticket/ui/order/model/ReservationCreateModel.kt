package com.superman.movieticket.ui.order.model

data class ReservationCreateModel(
    var screeningId: String,
    var seatReservations: List<SeatReservationsCreateModel>,
    var serviceReservations: List<ServiceReservationsCreateModel>
)
