package com.superman.movieticket.ui.order.payment.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.domain.services.ReservationService
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PaymentActivityViewModel @Inject constructor(
    private val reservationService: ReservationService

) : ViewModel () {
    val _apiLoading = MutableStateFlow(ApiState.NONE)
    val apiLoading = _apiLoading.asStateFlow()


    fun HandleCreateReservationAsync (reservationCreateModel: ReservationCreateModel) {
        viewModelScope.launch {
            _apiLoading.value = ApiState.LOADING
            reservationService.HandleCreateReservationCreateAsync(reservationCreateModel).enqueue(object : retrofit2.Callback<SuccessResponse<Reservation>> {
                override fun onResponse(
                    call: Call<SuccessResponse<Reservation>>,
                    response: Response<SuccessResponse<Reservation>>
                ) {


                    _apiLoading.value = ApiState.SUCCESS
                }

                override fun onFailure(call: Call<SuccessResponse<Reservation>>, t: Throwable) {
                    Log.d(call.javaClass.name, t.toString())
                    _apiLoading.value = ApiState.FAIL
                }

            })




            _apiLoading.value = ApiState.NONE
        }
    }

}