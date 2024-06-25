package com.superman.movieticket.domain.entities

import com.superman.movieticket.core.entity.BaseEntity
import java.io.Serializable

data class Category (
    var name: String
) : BaseEntity(), Serializable