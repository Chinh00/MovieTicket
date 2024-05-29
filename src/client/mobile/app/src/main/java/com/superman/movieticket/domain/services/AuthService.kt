package com.superman.movieticket.domain.services

import android.telecom.Call
import com.superman.movieticket.ui.auth.model.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("/connect/token")
    fun getToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): retrofit2.Call<TokenResponse>
}
