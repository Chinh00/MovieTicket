package com.superman.movieticket.ui.order.payment.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.domain.entities.Transaction
import com.superman.movieticket.domain.services.RegisterTransactionModel
import com.superman.movieticket.domain.services.ReservationService
import com.superman.movieticket.domain.services.TransactionService
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PaymentActivityViewModel @Inject constructor(
    private val reservationService: ReservationService,
    private val transactionService: TransactionService

) : ViewModel () {
    val _apiLoading = MutableStateFlow(ApiState.NONE)
    val apiLoading = _apiLoading.asStateFlow()
    lateinit var hubConnection: HubConnection

    val _transactionStatus = MutableStateFlow<String?>(null)
    val transactionStatus = _transactionStatus.asStateFlow()



    public fun HandleCreateTransactionAsync (total: Long) {
        viewModelScope.launch {
            transactionService.HandleRegisterTransactionAsync(RegisterTransactionModel(
                total
            )).enqueue(object: Callback<SuccessResponse<Transaction>> {
                override fun onResponse(
                    call: Call<SuccessResponse<Transaction>>,
                    response: Response<SuccessResponse<Transaction>>
                ) {
                    _transactionStatus.value = response?.body()?.data?.id
                    Log.d("Chinh", response?.body()?.data?.id.toString())

                }

                override fun onFailure(call: Call<SuccessResponse<Transaction>>, t: Throwable) {
                    Log.d("Chinh", t.toString())
                }

            })
        }
    }





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