package com.superman.movieticket.ui.home.control

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.services.MovieService
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
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val movieService: MovieService) :ViewModel(){

    private var _listMoviesNowing = mutableStateOf<List<Movie>>(
        emptyList()
    )
    val listMoviesNowing: State<List<Movie>> = _listMoviesNowing
    val _listMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val listMovies: StateFlow<List<Movie>> = _listMovies.asStateFlow()


    init {
        fetchMovies()
        viewModelScope.launch {
            movieService.HandleGetMoviesAsync("").enqueue(object : Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    _listMovies.value = response.body()?.data?.items!!
                    Log.d("Chinh", response.body()?.data?.items?.size.toString())
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    t: Throwable
                ) {
                    Log.d("Movie error", t.message.toString())
                }

            })
        }
    }
    public fun fetchMovies() {
        /*viewModelScope.launch {
            movieService.HandleGetMoviesAsync("").enqueue(object: Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    listMovie.value = response.body()?.data?.items!!
                    Log.d("Movie get list", response.body()?.data?.items.toString())
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    t: Throwable
                ) {
                }

            })



        }*/
    }
}
//val listMovies1 = listOf(
//    Movie(
//        name = "Avatar",
//        releaseDate = Date(),
//        totalTime = 162,
//        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
//        trailer = "0",
//        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
//
//    ),
//    Movie(
//        name = "Avatar",
//        releaseDate = Date(),
//        totalTime = 162,
//        description = "A paraplegic marine dispatched to the moon Pando/ra on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
//        trailer = "0",
//        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
//
//    ),Movie(
//        name = "Avatar",
//        releaseDate = Date(),
//        totalTime = 162,
//        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
//        trailer = "0",
//        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
//
//    ),Movie(
//        name = "Avatar",
//        releaseDate = Date(),
//        totalTime = 162,
//        description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
//        trailer = "0",
//        avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
//
//    ),)

