package com.bezatretailernew.bezat.models.getOfferCodes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOfferCodesResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private List<OfferCodeData> data = null;

    public List<OfferCodeData> getData() {
        return data;
    }

    public void setData(List<OfferCodeData> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
