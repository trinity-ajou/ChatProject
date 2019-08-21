package com.example.chatproject

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Msg(
    var email: String? = "",
    var text: String? = ""

)
