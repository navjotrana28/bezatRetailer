package com.bezatretailernew.bezat.api


import android.util.Log
import com.bezatretailernew.bezat.R
import com.bezatretailernew.bezat.activities.RegistrationActivity
import com.bezatretailernew.bezat.utils.Loader
import com.bezatretailernew.bezat.utils.URLS
import com.bezatretailernew.bezat.utils.toList
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("mobile_code")
    val mobileCode: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
){
    fun generate() = Gson().toJson(this)
    fun register(context:RegistrationActivity, onSuccess: (RegisterResponse)->Unit,onError:(String)->Unit){
        val dialog = Loader(context)
        dialog.show()
        URLS.REGISTER_URL.httpPost(generate().toList()).responseObject(CommonDeserializer(RegisterResponse::class.java)){_,_,result->
            if(dialog.isShowing)
                dialog.dismiss()
            val(response, error) = result
            if(error == null) {
                if (response != null && response.status == "success") {
                    Log.d("RegisterResponse", response.toString())
                    onSuccess(response)
                }
                else
                {
                    onError(response?.errorMessage?:context.getString(R.string.someting_wrong))
                }
            }
            else {
                onError(context.getString(R.string.someting_wrong))
                error.printStackTrace()
            }
        }
    }
}