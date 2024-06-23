package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.entities.Transaction
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionService {
    @POST("/client-api/Transaction")
    fun HandleRegisterTransactionAsync(@Body model: RegisterTransactionModel): Call<SuccessResponse<Transaction>>
}

data class RegisterTransactionModel (var total: Long)