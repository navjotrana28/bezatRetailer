package com.bezatretailer.bezat.api


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("user_name")
    val user_name: String,
    @SerializedName("user_type")
    val user_type: String,
    @SerializedName("email")
    val email: String
)