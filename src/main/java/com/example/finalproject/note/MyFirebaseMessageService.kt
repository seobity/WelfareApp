package com.example.finalproject.note

import android.util.Log
import com.example.finalproject.MyNotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if(message.data.isNotEmpty()){
            Log.d("25android","${message.data}")

            // 알림 보여주기
            val helper = MyNotificationHelper(this)
            helper.showNotification(message.data.get("title"),message.data.get("value"))
        }
    }
}