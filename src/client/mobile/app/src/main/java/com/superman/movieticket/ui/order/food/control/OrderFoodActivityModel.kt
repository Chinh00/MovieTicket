package com.superman.movieticket.ui.order.food.control

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.R
import com.superman.movieticket.core.entity.BaseEntity
import com.superman.movieticket.domain.entities.Service
import com.superman.movieticket.domain.services.ServiceService
import com.superman.movieticket.infrastructure.networks.XQueryHeader
import com.superman.movieticket.infrastructure.networks.defaultXQueryHeader
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
class OrderFoodActivityModel
    @Inject constructor(
        private val service: ServiceService
    )
    : ViewModel() {
    private val _text = MutableStateFlow("Hello World!!")
    val text: StateFlow<String> = _text
    private var _id = MutableStateFlow(0)
    val id: StateFlow<Int> = _id

    val _apiState = MutableStateFlow(ApiState.NONE)
    val apiState = _apiState.asStateFlow()


    val _listService = MutableStateFlow(emptyList<Service>())
    val listService = _listService.asStateFlow()

    private var _quantity = MutableStateFlow(0)
    val quantity: StateFlow<Int> = _quantity

    private var _totalPriceFood = MutableStateFlow(0)
    val totalPriceFood: StateFlow<Int> = _totalPriceFood

    init {
        HandleGetServices()
    }

    fun HandleGetServices() {
        viewModelScope.launch {
            _apiState.value = ApiState.LOADING
            val xQueryHeader = defaultXQueryHeader.copy()

            service.HandleGetServicesAsync(xQueryHeader.JsonSerializer())
                .enqueue(object : Callback<SuccessResponse<ListResponse<Service>>> {
                    override fun onResponse(
                        call: Call<SuccessResponse<ListResponse<Service>>>,
                        response: Response<SuccessResponse<ListResponse<Service>>>
                    ) {
                        _listService.value = response.body()?.data?.items ?: emptyList()
                        _apiState.value = ApiState.SUCCESS
                    }

                    override fun onFailure(
                        call: Call<SuccessResponse<ListResponse<Service>>>,
                        t: Throwable
                    ) {
                        Log.d("Chinh", t.message.toString())
                        _apiState.value = ApiState.FAIL
                    }

                })
            _apiState.value = ApiState.NONE
        }
    }


    fun setQuantity(newQuantity: Int) {
        _quantity.value = newQuantity
    }

    fun setTotalPrice(newTotal: Int) {
        _totalPriceFood.value = newTotal
    }

}


