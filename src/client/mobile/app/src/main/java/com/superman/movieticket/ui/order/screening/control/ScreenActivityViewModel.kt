package com.superman.movieticket.ui.order.screening.control

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Room
import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.domain.services.ScreeningService
import com.superman.movieticket.infrastructure.networks.FilterModel
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ScreenActivityViewModel @Inject constructor(
    private val screeningService: ScreeningService
) : ViewModel() {




    val _listRoom = MutableStateFlow(emptyList<Screening>())
    val listRoom = _listRoom.asStateFlow()

    val _apiState = MutableStateFlow(ApiState.NONE)
    val apiState = _apiState.asStateFlow()



    @RequiresApi(Build.VERSION_CODES.O)
    fun HandleGetRoomWithMovieAndDate(movieId: String, startDate: LocalDateTime, endDate: LocalDateTime) {
        viewModelScope.launch {
            _apiState.value = ApiState.LOADING
            _listRoom.value = emptyList()

            val getRoomWithMovieAndDate = defaultXQueryHeader.copy()
            getRoomWithMovieAndDate.apply {
                filters = mutableListOf(
                    FilterModel(
                        fieldName = "movieId",
                        comparision = "==",
                        fieldValue = movieId
                    ),
                    FilterModel(
                        fieldName = "startDate",
                        comparision = ">=",
                        fieldValue = startDate.toString()
                    ),
                    FilterModel(
                        fieldName = "startDate",
                        comparision = "<",
                        fieldValue = endDate.toString()
                    ),
                )
                includes = mutableListOf("Room")
            }
            screeningService.HandleGetScreeningsAsync(getRoomWithMovieAndDate.JsonSerializer()).enqueue(object: Callback<SuccessResponse<ListResponse<Screening>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Screening>>>,
                    response: Response<SuccessResponse<ListResponse<Screening>>>
                ) {
                    _listRoom.value = response.body()?.data?.items ?: emptyList()
                    _apiState.value = ApiState.SUCCESS
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Screening>>>,
                    t: Throwable
                ) {
                    Log.d("Chinh", t.message.toString())
                    _apiState.value = ApiState.FAIL
                }

            })
            _apiState.value = ApiState.NONE
        }
    }








}

