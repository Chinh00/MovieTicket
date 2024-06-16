package com.superman.movieticket.ui.order.payment.hooks

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.payment.PaymentActivity

fun NavigatePaymentTicket (
    context: Context,
    reservationCreateModel: ReservationCreateModel
){
    val intent = Intent(context, PaymentActivity::class.java)
    intent.putExtra("ReservationCreateModel", Gson().toJson(reservationCreateModel))
    context.startActivity(intent)

}