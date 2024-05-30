package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity
import java.math.BigInteger
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class Movie (
    val id: String,
    val createdDate: LocalDateTime,
    var updateDate: LocalDateTime? = null,
    var name: String,
    var releaseDate: String,
    val totalTime: Number,
    val description: String,
    val avatar: String,
    val trailer: String,
)

