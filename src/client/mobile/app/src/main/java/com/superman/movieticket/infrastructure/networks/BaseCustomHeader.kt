package com.superman.movieticket.infrastructure.networks


abstract class BaseCustomHeader {
    abstract fun JsonSerializer(): String
}