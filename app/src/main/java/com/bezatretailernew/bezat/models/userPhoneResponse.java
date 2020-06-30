package com.bezatretailernew.bezat.models;

import com.bezatretailernew.bezat.models.packageResponse.PackageData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class userPhoneResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private userPhoneData data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public userPhoneData getData() {
        return data;
    }

    public void setData(userPhoneData data) {
        this.data = data;
    }
}
