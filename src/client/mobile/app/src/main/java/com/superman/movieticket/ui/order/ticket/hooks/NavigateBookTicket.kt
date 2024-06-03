package com.superman.movieticket.ui.order.ticket.hooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.ui.order.ticket.TicketBookActivity



fun NavigateBookTicket (
    context: Context,
    screening: Screening
) {
    val intent = Intent(context, TicketBookActivity::class.java)
    intent.putExtra("screening", Gson().toJson(screening))
    context.startActivity(intent)
}