package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity
import java.math.BigInteger
import java.util.Date

data class Movie (
    var name: String,
    var releaseDate: Date,
    val totalTime: Number,
    val description: String,
    val avatar: String,
    val trailer: String,
): BaseEntity()

