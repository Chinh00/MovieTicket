package com.superman.movieticket.infrastructure.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Xử lý token mới
        Log.d("token", token);
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Xử lý tin nhắn FCM khi nhận được
    }

    private fun sendRegistrationToServer(token: String) {
        // Gửi token tới server của bạn (nếu cần thiết)
        // Ví dụ:
        // val url = "https://example.com/register_token"
        // val request = Request.Builder().url(url).post(RequestBody.create(MediaType.parse("text/plain"), token)).build()
        // OkHttpClient().newCall(request).execute()
    }
}