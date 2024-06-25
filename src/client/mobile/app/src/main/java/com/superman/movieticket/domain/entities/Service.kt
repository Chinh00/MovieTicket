package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity

data class Service (
    var name: String,
    var unit: String,
    var priceUnit: Int,
    var avatar: String,
) : BaseEntity()