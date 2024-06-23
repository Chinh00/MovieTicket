package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class Transaction (
    val reservationId: String,
    val total: Long
): BaseEntity()