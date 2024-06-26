package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity
import java.io.Serializable

data class Seat (
    val id: String,
    val rowNumber: String,
    val colNumber: Number,
    val isPlaced: Boolean
):Serializable