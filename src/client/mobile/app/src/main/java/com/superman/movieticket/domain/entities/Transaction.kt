package com.superman.movieticket.domain.entities

import com.google.gson.annotations.SerializedName
import com.superman.movieticket.core.entity.BaseEntity

data class Transaction (
    @SerializedName("ReservationId")

    val reservationId: String,
    @SerializedName("total")
    val total: Long
): BaseEntity()