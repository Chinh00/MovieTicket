package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity
import java.io.Serializable

data class Room (
    val roomNumber: Number,
    val seats: List<Seat>
) : BaseEntity(),Serializable