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
    val avatar: String


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
        avatar = "http://ia.media-imdb.com/images/M/MV5BMTU4NzMyNDk1OV5BMl5BanBnXkFtZTcwOTEwMzU1MQ@@._V1_SX300.jpg"

    ),
    Movie(
        id = "2",
        title = "I Am Legend",
        releaseDate = "18 Dec 2009",
        duration = "162 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = "http://ia.media-imdb.com/images/M/MV5BMTU4NzMyNDk1OV5BMl5BanBnXkFtZTcwOTEwMzU1MQ@@._V1_SX300.jpg"

    ), Movie(
        id = "3",
        title = "300",
        releaseDate = "09 Mar 2007",
        duration = "117 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = "http://ia.media-imdb.com/images/M/MV5BMjAzNTkzNjcxNl5BMl5BanBnXkFtZTYwNDA4NjE3._V1_SX300.jpg"

    ), Movie(
        id = "4",
        title = "The Avengers",
        releaseDate = "04 May 2012",
        duration = "143 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = "http://ia.media-imdb.com/images/M/MV5BMTk2NTI1MTU4N15BMl5BanBnXkFtZTcwODg0OTY0Nw@@._V1_SX300.jpg"

    ), Movie(
        id = "5",
        title = "The Wolf of Wall Street",
        releaseDate = "18 Dec 2009",
        duration = "162 min",
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = 0,
        price = 10000.0,
        avatar = "http://ia.media-imdb.com/images/M/MV5BMjIxMjgxNTk0MF5BMl5BanBnXkFtZTgwNjIyOTg2MDE@._V1_SX300.jpg"

    )

)
