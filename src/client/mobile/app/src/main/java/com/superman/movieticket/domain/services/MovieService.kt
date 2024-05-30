package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieService {

    @GET("/client-api/Movie")
    fun HandleGetMoviesAsync(@Header("x-query") query: String): retrofit2.Call<SuccessResponse<ListResponse<Movie>>>
}