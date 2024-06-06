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

    fun HandleGetServices () {
        _apiState.value = ApiState.LOADING
        var xQueryHeader = defaultXQueryHeader.copy()

        service.HandleGetServicesAsync(xQueryHeader.JsonSerializer()).enqueue(object: Callback<SuccessResponse<ListResponse<Service>>> {
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


    fun setQuantity(newQuantity:Int){
        _quantity.value=newQuantity
    }
    fun setTotalPrice(newTotal:Int){
        _totalPriceFood.value=newTotal
    }
    fun updateText(newText: String, newId: Int) {
        viewModelScope.launch {
            _text.value = newText
            _id.value = newId
        }
    }

    fun loadingListFood(): List<MovieFood> {
        return listFood
    }

    private val listFood = listOf(
        MovieFood(R.drawable.bong, "Bap nam Ga 1 (Sweet)", 60.000),
        MovieFood(R.drawable.bong, "Bap nam Ga 2 (Sweet)", 95.000),
        MovieFood(R.drawable.bong, "Bap nam Ga 3 (Sweet)", 120.000),
        MovieFood(R.drawable.bong, "Bap nam Ga 4 (Sweet)", 135.000),
        MovieFood(R.drawable.bong, "Bap nam Ga 5 (Sweet)", 210.000)

    )

}


data class MovieFood(
    @DrawableRes val img: Int,
    val name: String, val price: Number
) : BaseEntity()


