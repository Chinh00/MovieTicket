package com.superman.movieticket.ui.film.control

import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.infrastructure.utils.ApiState
import kotlinx.coroutines.flow.StateFlow

interface FilmScreenViewModel {
    val listFilmShowing: StateFlow<List<Movie>>
    val listFilmComingSoon: StateFlow<List<Movie>>

    val apiState: StateFlow<ApiState>

    fun getListFilmShowing()
    fun getListFilmComingSoon()
}