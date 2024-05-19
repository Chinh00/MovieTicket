package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class Screening (
    val movieId: String,
    val roomId: String,
    val startDate: String,
    val movie: Movie,
    val room: Room
) : BaseEntity()