package com.superman.movieticket.ui.order.food.hooks

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.ui.order.food.OrderFoodActivity
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.model.ReservationExtendModel

fun NavigateOrderFood(
    context: Context,
    reservationCreateModel: ReservationCreateModel,
    reservationExtendModel: ReservationExtendModel
) {
    val intent = Intent(context, OrderFoodActivity::class.java)
    intent.putExtra("ReservationCreateModel", Gson().toJson(reservationCreateModel))
    intent.putExtra("TotalPrice", Gson().toJson(reservationExtendModel))
    context.startActivity(intent)
}