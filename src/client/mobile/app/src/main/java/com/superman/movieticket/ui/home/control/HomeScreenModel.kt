package com.superman.movieticket.ui.home.control

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Movie
import kotlinx.coroutines.launch
import java.util.Date

class HomeScreenModel :ViewModel(){

    private var _listMovies = mutableStateOf<List<Movie>>(
        emptyList()
    )
    val listMovies: State<List<Movie>> = _listMovies

    init {
        fetchMovies()
    }
    private fun fetchMovies() {
        viewModelScope.launch {
            val movies = listMovies1
            _listMovies.value = movies
        }
    }
}
val listMovies1 = listOf(
    Movie(
        name = "Avatar",
        releaseDate = Date(),
        totalTime = 162,
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = "0",
        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"

    ),
    Movie(
        name = "Avatar",
        releaseDate = Date(),
        totalTime = 162,
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = "0",
        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"

    ),Movie(
        name = "Avatar",
        releaseDate = Date(),
        totalTime = 162,
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = "0",
        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"

    ),Movie(
        name = "Avatar",
        releaseDate = Date(),
        totalTime = 162,
        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
        trailer = "0",
        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"

    ),)


