package com.superman.movieticket.core.entity

import java.util.Date

open class BaseEntity {
    lateinit var id: String
    lateinit var createdDate: Date
    var updateDate: Date? = null
}