package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class User(
    val name:String,
    val avatar:String,
    val phone:String,

):BaseEntity()