package com.superman.movieticket.ui.home.control

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.services.MovieService
import com.superman.movieticket.infrastructure.networks.FilterModel
import com.superman.movieticket.infrastructure.networks.XQueryHeader
import com.superman.movieticket.infrastructure.networks.defaultXQueryHeader
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieService: MovieService
) : ViewModel() {
    private val _apiState = MutableStateFlow(ApiState.LOADING)
    val apiState = _apiState.asStateFlow()

    private val _listMovies = MutableStateFlow(emptyList<Movie>())
    val listMovies = _listMovies.asStateFlow()

    init {
        handleGetMovie()
    }

    private fun handleGetMovie() {
        viewModelScope.launch {
            _apiState.emit(ApiState.LOADING)
            val getMovie = defaultXQueryHeader.copy(
                includes = mutableListOf("Categories"),
                sortBy = mutableListOf("Id")
            )

            movieService.HandleGetMoviesAsync(getMovie.JsonSerializer())
                .enqueue(object : Callback<SuccessResponse<ListResponse<Movie>>> {
                    override fun onResponse(
                        call: Call<SuccessResponse<ListResponse<Movie>>>,
                        response: Response<SuccessResponse<ListResponse<Movie>>>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.data?.items
                            if (movies != null && movies.isNotEmpty()) {
                                _listMovies.value = movies
                                _apiState.value = ApiState.SUCCESS
                            } else {
                                _listMovies.value = emptyList()  // Cập nhật danh sách trống nếu không có phim
                                _apiState.value = ApiState.FAIL
                            }
                        } else {
                            Log.d("Error response", "Unsuccessful response: ${response.code()}")
                            _apiState.value = ApiState.FAIL
                        }
                    }

                    override fun onFailure(
                        call: Call<SuccessResponse<ListResponse<Movie>>>,
                        t: Throwable
                    ) {
                        Log.d("Error call api", t.message.toString())
                        _listMovies.value = emptyList()  // Cập nhật
                        _apiState.value = ApiState.FAIL
                    }
                })
        }
    }
}
