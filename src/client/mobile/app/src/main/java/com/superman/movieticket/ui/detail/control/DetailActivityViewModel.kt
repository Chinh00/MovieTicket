package com.superman.movieticket.ui.detail.control

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.superman.movieticket.domain.entities.Movie
import java.util.Date

class DetailActivityViewModel : ViewModel() {
    private val _movie = MutableLiveData<Movie>()

    val movie: LiveData<Movie> get() = _movie
    fun setMovie(id:Int) {
        val m = Movie(
            name = "Lật Mật 6",
            releaseDate = "",
            totalTime = 162,
            description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home. Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier",
            trailer = "OobBWy3avUo",
            avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQE8-BC84GcTHqpPmer61cugKcZYGYot_O76Q&s"

        )

        _movie.value = m
    }
}