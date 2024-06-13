package com.superman.movieticket.domain.entities

import androidx.datastore.core.Serializer
import com.superman.movieticket.core.entity.BaseEntity
import java.io.Serializable
import java.math.BigInteger
import java.util.Date

data class Movie (
    var name: String,
    var releaseDate: String,
    val totalTime: Number,
    val description: String,
    val avatar: String,
    val trailer: String,
    val categories: List<Category>
): BaseEntity(),Serializable

