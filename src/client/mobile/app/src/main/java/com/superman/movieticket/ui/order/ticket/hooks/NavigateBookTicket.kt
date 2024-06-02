package com.superman.movieticket.ui.order.ticket.hooks

import android.content.Context
import android.content.Intent
import com.superman.movieticket.ui.order.ticket.TicketBookActivity



fun NavigateBookTicket (
    context: Context,
    screenId: String
) {
    val intent = Intent(context, TicketBookActivity::class.java)
    intent.putExtra("screenId", screenId)
    context.startActivity(intent)
}