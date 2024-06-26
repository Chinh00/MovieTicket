package com.superman.movieticket.ui.history.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.domain.services.ReservationService
import com.superman.movieticket.infrastructure.networks.XQueryHeader
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
class ReservationViewModel @Inject constructor(private val apiService: ReservationService) : ViewModel() {

    private val _listOrderReservation = MutableStateFlow<List<Reservation>>(emptyList())
    val listOrderReservation: StateFlow<List<Reservation>> get() = _listOrderReservation
    private val _apiState = MutableStateFlow(ApiState.LOADING)
    val apiState = _apiState.asStateFlow()

    fun fetchReservedMovies()
    {
    viewModelScope.launch {
        _apiState.value=ApiState.LOADING
        val xQueryHeader = XQueryHeader(
            includes =
            mutableListOf("SeatReservations","ServiceReservations","Screening.Movie","Screening.Room","ServiceReservations.Service","SeatReservations.Seat"),
            filters = mutableListOf(),
            sortBy = mutableListOf("Id"),
            page = 1,
            pageSize = 5
        )
        val call = apiService.HandleGetReservationAsync(xQueryHeader.JsonSerializer())
            .enqueue(object : Callback<SuccessResponse<ListResponse<Reservation>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Reservation>>>,
                    response: Response<SuccessResponse<ListResponse<Reservation>>>
                ) {
                    Log.d("Chinh", response.body()?.data?.items.toString())
                    _listOrderReservation.value = response.body()?.data?.items ?: emptyList()

                    _apiState.value = ApiState.SUCCESS
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Reservation>>>,
                    t: Throwable
                ) {
                    Log.d("ReservationViewModel", "onFailure: ${t.message}")

                }

            })
        }
    }
}