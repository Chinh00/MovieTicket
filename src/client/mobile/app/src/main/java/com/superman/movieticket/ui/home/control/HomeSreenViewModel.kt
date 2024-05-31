package com.superman.movieticket.ui.home.control

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.services.MovieService
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val movieService: MovieService) : ViewModel(){
    val _apiState = MutableStateFlow(ApiState.LOADING)
    val apiState = _apiState.asStateFlow()

    val _listMovies = MutableStateFlow(emptyList<Movie>())
    val listMovies = _listMovies.asStateFlow()


    init {
        viewModelScope.launch {
            _apiState.emit(ApiState.LOADING)
            delay(2000)
            movieService.HandleGetMoviesAsync("").enqueue(object: Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    _listMovies.value = response.body()?.data?.items!!
                    _apiState.value = ApiState.SUCCESS
                    Log.d("Chinh", response.body()?.data?.items?.size.toString())
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    t: Throwable
                ) {
                    _apiState.value = ApiState.FAIL
                    Log.d("Error call api", t.message.toString())
                }

            })
            _apiState.emit(ApiState.NONE)
        }

    }

}
