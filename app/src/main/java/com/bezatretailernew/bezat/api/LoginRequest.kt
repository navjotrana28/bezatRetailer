package com.bezatretailernew.bezat.api


import com.bezatretailernew.bezat.activities.LoginActivity
import com.bezatretailernew.bezat.utils.Loader
import com.bezatretailernew.bezat.utils.PreferenceManager
import com.bezatretailernew.bezat.utils.URLS
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("device_id")
    val deviceId: String = PreferenceManager.instance.deviceId,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
) {
    fun generate() = Gson().toJson(this)
    fun login(context:LoginActivity, onsuccess: (LoginResponse) -> Unit, onError: () -> Unit) {
        val loader = Loader(context)
        loader.show()
        URLS.LOGIN_URL.httpPost(listOf("device_id" to deviceId, "email" to email, "password" to password)).responseObject(CommonDeserializer(LoginResponse::class.java)) { _, _, result ->
            if(loader.isShowing)
                loader.dismiss()
            val(response,error) = result
            if(error == null && response!=null){

                onsuccess(response)
            }
            else
            {
                onError()
            }
        }
    }
}