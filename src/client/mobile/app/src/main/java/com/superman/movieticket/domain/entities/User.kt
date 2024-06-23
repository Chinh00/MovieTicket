package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class User(
    val userName: String,
    val email: String,
    val phoneNumber: String
):BaseEntity()