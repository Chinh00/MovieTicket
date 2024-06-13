package com.superman.movieticket.ui.film.control

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
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

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getListFilmShowing() {
        viewModelScope.launch {
            val xQueryHeader = XQueryHeader(
                includes = mutableListOf(),
                filters = mutableListOf(),
                sortBy = mutableListOf(),
                page = 1,
                pageSize = 20
            )
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            val filterModel = FilterModel(
                fieldName = "releaseDate",
                comparision = "<=",
                fieldValue = formatter.format(currentDateTime)
            )
            xQueryHeader.sortBy.add("releaseDateDesc")
            xQueryHeader.filters.add(filterModel)


            _apiState.value = ApiState.LOADING

            movieService.HandleGetMoviesAsync(xQueryHeader.JsonSerializer()).enqueue(object: Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    _listFilmShowing.value = response.body()?.data?.items ?: emptyList()
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

    override fun getListFilmComingSoon() {
        viewModelScope.launch {
            _apiState.value = ApiState.LOADING
            val xQueryHeader = defaultXQueryHeader.copy()
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            val filterModel = FilterModel(
                fieldName = "releaseDate",
                comparision = ">",
                fieldValue = formatter.format(currentDateTime)
            )
            xQueryHeader.filters.add(filterModel)

            movieService.HandleGetMoviesAsync(xQueryHeader.JsonSerializer()).enqueue(object: Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    _listFilmComingSoon.value = response.body()?.data?.items!!
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