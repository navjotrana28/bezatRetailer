package com.bezatretailernew.bezat.models.packageResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;

    @SerializedName("data")
    @Expose
    private List<PackageData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public List<PackageData> getData() {
        return data;
    }

    public void setData(List<PackageData> data) {
        this.data = data;
    }
}
