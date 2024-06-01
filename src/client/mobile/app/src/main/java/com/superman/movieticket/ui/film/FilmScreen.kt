package com.superman.movieticket.ui.film

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.superman.movieticket.ui.film.control.FilmScreenViewModel


@Composable
fun FilmScreen () {
    val filmScreenViewModel: FilmScreenViewModel = hiltViewModel()

    //list film showing
    val listFilmShowing = filmScreenViewModel.listFilmShowing.collectAsState()
    // list film ComingSoon
    val listFilmComingSoon = filmScreenViewModel.listFilmComingSoon.collectAsState()

    Text(text = "ListFilm")


}