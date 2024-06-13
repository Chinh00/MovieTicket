package com.superman.movieticket.ui.detail.control

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.services.MovieService
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.Date
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel
class DetailActivityViewModel @Inject constructor(private val movieService: MovieService) :
    ViewModel() {
    private val _movie = MutableLiveData<Movie>()

    val movie: LiveData<Movie> get() = _movie


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    fun getMovieById(id: String) {

        viewModelScope.launch {
            movieService.HandleGetMovieByIdAsync(id).enqueue(object:
                retrofit2.Callback<SuccessResponse<Movie>> {
                override fun onResponse(
                    call: Call<SuccessResponse<Movie>>,
                    response: Response<SuccessResponse<Movie>>
                ) {
                    if (response.isSuccessful) {
                        _movie.postValue(response.body()?.data)
                    } else {
                        _errorMessage.postValue("Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SuccessResponse<Movie>>, t: Throwable) {
                    _errorMessage.postValue("Failure: ${t.message}")
                }

            })
        }
//        val m = Movie(
//            name = "Lật Mật 6",
//            releaseDate = "",
//            totalTime = 162,
//            description = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home. Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier Modifier",
//            trailer = "OobBWy3avUo",
//            avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQE8-BC84GcTHqpPmer61cugKcZYGYot_O76Q&s"
//
//        )


    }
}