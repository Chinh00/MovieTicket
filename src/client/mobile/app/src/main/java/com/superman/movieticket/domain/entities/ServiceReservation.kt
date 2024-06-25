package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class ServiceReservation (
    var reservationId: String,
    var reservation: String,
    var serviceId: String,
    var service: Service,
    var quantity: Int,
    var price: Long
) : BaseEntity()