package com.superman.movieticket.domain.services

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

data class Post(val userId: String, val id: String, val title: String, val body: String);

interface TestService {
    @GET("/post")
    fun GetPost(): Call<Response<Post>>;
}