package com.superman.movieticket.ui.film.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.services.MovieService
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class FilmScreenViewModelImpl
    @Inject constructor(
        private val movieService: MovieService
    ) : ViewModel(), FilmScreenViewModel
{
    val _listFilmShowing = MutableStateFlow<List<Movie>>(emptyList())
    val _listFilmComingSoon = MutableStateFlow<List<Movie>>(emptyList())
    val _apiState = MutableStateFlow<ApiState>(ApiState.NONE)

    override val listFilmShowing: StateFlow<List<Movie>>
        get() = _listFilmShowing.asStateFlow()
    override val listFilmComingSoon: StateFlow<List<Movie>>
        get() = _listFilmComingSoon.asStateFlow()
    override val apiState: StateFlow<ApiState>
        get() = _apiState.asStateFlow()

    init {
        viewModelScope.launch {
            getListFilmShowing()
            getListFilmComingSoon()
        }
    }


    override suspend fun getListFilmShowing() {
        viewModelScope.launch {
            _apiState.value = ApiState.LOADING
            movieService.HandleGetMoviesAsync("").enqueue(object: Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    _listFilmShowing.value = response.body()?.data?.items!!
                    _apiState.value = ApiState.SUCCESS
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    t: Throwable
                ) {
                    Log.d("Movie", "onFailure: ${t.message}")
                }

            })
        }
    }

    override suspend fun getListFilmComingSoon() {
        viewModelScope.launch {
            _apiState.value = ApiState.LOADING
            movieService.HandleGetMoviesAsync("").enqueue(object: Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    _listFilmShowing.value = response.body()?.data?.items!!
                    _apiState.value = ApiState.SUCCESS
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    t: Throwable
                ) {
                    Log.d("Movie", "onFailure: ${t.message}")
                }

            })
        }
    }
}