package com.superman.movieticket.domain.services

import android.telecom.Call
import com.superman.movieticket.domain.entities.User
import com.superman.movieticket.ui.auth.model.TokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part


interface AuthService {
    @FormUrlEncoded
    @POST("/client-identity/connect/token")
    fun getToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String
    ): retrofit2.Call<TokenResponse>

    @FormUrlEncoded
    @POST("/client-identity/connect/token")
    fun getTokenSocial(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("scope") scope: String,
        @Field("token") token: String,
        @Field("client_secret") secret: String,
        @Field("provider") provider: String

    ): retrofit2.Call<TokenResponse>

    @GET("/client-identity/api/Account")
    fun HandleGetUserDetail (): retrofit2.Call<User>

    @POST("/client-identity/api/Account/update-password")
    fun HandleUpdatePassword(@Body model: UserUpdatePasswordModel): retrofit2.Call<User>

    @Multipart
    @POST("/client-identity/api/Account")
    fun UpdateUserInfo(@Body model: UserUpdateInfoModel
    ): retrofit2.Call<User>

}


data class UserUpdatePasswordModel (
    val newPassword: String
)


data class UserUpdateInfoModel (
    var fullName: String,
    var birthday: String,
    var avatar: String,
    var userGender: Int
)