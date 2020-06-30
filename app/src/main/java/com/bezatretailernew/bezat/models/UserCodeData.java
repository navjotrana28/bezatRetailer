package com.bezatretailernew.bezat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCodeData {
    @SerializedName("user_code")
    @Expose
    private String user_code;

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_phone) {
        this.user_code = user_phone;
    }
}
