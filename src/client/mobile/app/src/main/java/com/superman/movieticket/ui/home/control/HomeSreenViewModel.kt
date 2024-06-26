package com.superman.movieticket.ui.home.control

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.services.MovieService
import com.superman.movieticket.domain.services.NotificationDeviceCreateModel
import com.superman.movieticket.domain.services.NotificationService
import com.superman.movieticket.infrastructure.networks.FilterModel
import com.superman.movieticket.infrastructure.networks.XQueryHeader
import com.superman.movieticket.infrastructure.networks.defaultXQueryHeader
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.PreferenceKey
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieService: MovieService,
    private val notificationService: NotificationService,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _apiState = MutableStateFlow(ApiState.LOADING)
    val apiState = _apiState.asStateFlow()

    private val _listMovies1 = MutableStateFlow(emptyList<Movie>())
    val listMovies1 = _listMovies1.asStateFlow()

    init {
        handleGetMovie()
        viewModelScope.launch {
            val check = dataStore.data.first()[PreferenceKey.IS_PUSH_TOKEN_NOTI]

            if (check == null) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener  { task ->
                    Log.d("Chinh", task.result)
                    notificationService.RegisterDevice(
                        NotificationDeviceCreateModel(
                            token = task.result,
                            userId = "19585d7e-dd1f-4dab-ace3-fa485a0ac89a",
                            deviceId = ""
                        )
                    ).enqueue(object: Callback<SuccessResponse<String>> {
                        override fun onResponse(
                            call: Call<SuccessResponse<String>>,
                            response: Response<SuccessResponse<String>>
                        ) {
                        }

                        override fun onFailure(call: Call<SuccessResponse<String>>, t: Throwable) {
                        }
                    })

                }
            }
            dataStore.edit {
                it[PreferenceKey.IS_PUSH_TOKEN_NOTI] = "true"
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleGetMovie() {
        viewModelScope.launch {
            val xQueryHeader = XQueryHeader(
                includes = mutableListOf("Categories"),
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
                fieldValue = currentDateTime.toString()
            )
            xQueryHeader.sortBy.add("releaseDateDesc")
            xQueryHeader.filters.add(filterModel)


            _apiState.value = ApiState.LOADING

            movieService.HandleGetMoviesAsync(xQueryHeader.JsonSerializer()).enqueue(object: Callback<SuccessResponse<ListResponse<Movie>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Movie>>>,
                    response: Response<SuccessResponse<ListResponse<Movie>>>
                ) {
                    _listMovies1.value = response.body()?.data?.items ?: emptyList()

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
