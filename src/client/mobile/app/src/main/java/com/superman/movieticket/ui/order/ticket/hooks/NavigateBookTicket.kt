package com.superman.movieticket.ui.order.ticket.hooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.ticket.TicketBookActivity



fun NavigateBookTicket (
    context: Context,
    reservationCreateModel: ReservationCreateModel
) {
    val intent = Intent(context, TicketBookActivity::class.java)
    intent.putExtra("ReservationCreateModel", Gson().toJson(reservationCreateModel))
    context.startActivity(intent)
}