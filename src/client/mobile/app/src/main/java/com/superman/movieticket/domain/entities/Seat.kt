package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class Seat (
    val id: String,
    val rowNumber: Number,
    val colNumber: Number
)