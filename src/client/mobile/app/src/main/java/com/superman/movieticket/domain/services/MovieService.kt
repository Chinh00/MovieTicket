package com.superman.movieticket.domain.services

import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.domain.entities.Room
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MovieService {
    @GET("/client-api/Movie")
    fun HandleGetMoviesAsync(@Header("x-query") query: String): Call<SuccessResponse<ListResponse<Movie>>>

    @GET("/client-api/Movie/{id}")
    fun HandleGetMovieByIdAsync(@Path("id") id: String): Call<SuccessResponse<Movie>>


}