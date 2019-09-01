package com.example.chatproject

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatList_Data(
        var email: String? = "",
        var text: String? = ""
)