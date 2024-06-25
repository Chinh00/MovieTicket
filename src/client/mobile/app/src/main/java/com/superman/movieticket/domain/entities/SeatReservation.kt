package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class SeatReservation (
    var rowNumber: String,
    var colNumber: String
) : BaseEntity()