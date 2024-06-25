package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class User(
    var fullName: String,
    var avatar: String,
    var birthday: String,
    var userGender: Int,
    var userName: String,
    var email: String,
    var phoneNumber: String,
    var passwordHash: String?
):BaseEntity()