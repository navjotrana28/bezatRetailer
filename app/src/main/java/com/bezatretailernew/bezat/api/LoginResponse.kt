package com.bezatretailernew.bezat.api

import com.bezatretailernew.bezat.R
import com.bezatretailernew.bezat.activities.LoginActivity
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("userID")
    val userID: String?,
    @SerializedName("storeID")
    val storeID: String?,
    @SerializedName("userInfo")
    val userInfo: UserInfo?,
    @SerializedName("error_msg")
    val errorMessage: String?
){
   fun  handleLogin(context: LoginActivity, onSuccess:()->Unit, onError:(errorMessage:String)->Unit){
       when(status){
           "failed"-> {
               onError(errorMessage ?: context.getString(R.string.someting_wrong))
           }
           "success"->{
               onSuccess()
//               onError("success")
//               SharedPrefs.setKey(context,"userId",userID);
//               SharedPrefs.setKey(context,"storeID",storeID);
//               SharedPrefs.setKey(context,"LoggedIn","true");
//               context.startActivity(Intent(context, Homepage::class.java));
//               context.finishAffinity();

           }
           "pending"->{
               //TODO()
               //context.finish()
               onError("This is yet to implement. This is mostly the OTP case!!")
           }
           "inactive"->{
               onError(errorMessage?:context.getString(R.string.someting_wrong))
           }
       }
    }


}