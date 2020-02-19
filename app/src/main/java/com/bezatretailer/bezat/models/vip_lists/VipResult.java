package com.bezatretailer.bezat.models.vip_lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VipResult {

    @SerializedName("result")
    @Expose
    private List<VipData> result = null;

    public List<VipData> getResult() {
        return result;
    }

    public void setResult(List<VipData> result) {
        this.result = result;
    }

}

