package com.superman.movieticket.ui.order.food.hooks

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.ui.order.food.OrderFoodActivity
import com.superman.movieticket.ui.order.model.ReservationCreateModel

fun NavigateOrderFood(
    context: Context,
    reservation: ReservationCreateModel?
) {
    val intent = Intent(context, OrderFoodActivity::class.java)

    context.startActivity(intent)
}