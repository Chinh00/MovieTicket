package com.superman.movieticket.domain.entities

data class ServiceReservation (
    var reservationId: String,
    var reservation: String,
    var serviceId: String,
    var service: Service,
    var quantity: Int,
    var price: Long
) 