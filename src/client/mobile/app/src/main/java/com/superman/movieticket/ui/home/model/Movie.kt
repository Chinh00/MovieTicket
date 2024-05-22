package com.superman.movieticket.ui.home.model

import com.superman.movieticket.R

data class Movie(
    val id: String,
    val title: String,

    val releaseDate: String,
    val duration: String,
    val description: String,
    val trailer: Int?,
    val price: Double,
    val avatar: Int


)

val listMovies = listOf(
    Movie(
        id = "1",
        title = "Avatar",
        releaseDate = "18 Dec 2009",
        duration = "162 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = R.drawable.kungfu

    ),
    Movie(
        id = "2",
        title = "Avatar",
        releaseDate = "18 Dec 2009",
        duration = "162 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = R.drawable.kungfu

    ), Movie(
        id = "3",
        title = "Avatar",
        releaseDate = "18 Dec 2009",
        duration = "162 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = R.drawable.kungfu

    ), Movie(
        id = "4",
        title = "Avatar",
        releaseDate = "18 Dec 2009",
        duration = "162 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = R.drawable.kungfu

    ), Movie(
        id = "5",
        title = "Avatar",
        releaseDate = "18 Dec 2009",
        duration = "162 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = R.drawable.kungfu

    )

)
