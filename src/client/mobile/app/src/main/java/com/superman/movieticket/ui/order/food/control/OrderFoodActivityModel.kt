package com.superman.movieticket.ui.order.food.control

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.R
import com.superman.movieticket.core.entity.BaseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderFoodActivityModel : ViewModel() {
    private val _text = MutableStateFlow("Hello World!!")
    val text: StateFlow<String> = _text
    private var _id = MutableStateFlow(0)
    val id: StateFlow<Int> = _id



    private var _quantity = MutableStateFlow(0)
    val quantity: StateFlow<Int> = _quantity

    private var _totalPriceFood = MutableStateFlow(0)
    val totalPriceFood: StateFlow<Int> = _totalPriceFood


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


