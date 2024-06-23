package com.superman.movieticket.ui.history.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.domain.services.ReservationService
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailOrderViewModel @Inject constructor(private val apiService: ReservationService) : ViewModel() {

    private val _listOrderReservation = MutableStateFlow<List<Reservation>>(emptyList())
    val listOrderReservation: StateFlow<List<Reservation>> get() = _listOrderReservation

    fun fetchReservedMovies(query: String) {
        viewModelScope.launch {
            val call = apiService.HandleGetReservationAsync(query)
            call.enqueue(object : Callback<SuccessResponse<ListResponse<Reservation>>> {
                override fun onResponse(
                    call: Call<SuccessResponse<ListResponse<Reservation>>>,
                    response: Response<SuccessResponse<ListResponse<Reservation>>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.items?.let { Reservation ->
                            _listOrderReservation.value = Reservation
                            Log.i("DetailOrderViewModel", Reservation.toString() )
                        }
                    } else {
                        // Handle the error
                    }
                }

                override fun onFailure(
                    call: Call<SuccessResponse<ListResponse<Reservation>>>,
                    t: Throwable
                ) {
                    // Handle the error
                }
            })
        }
    }

}
