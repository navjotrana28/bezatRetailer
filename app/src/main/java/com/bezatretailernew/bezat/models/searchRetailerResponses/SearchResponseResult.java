package com.bezatretailernew.bezat.models.searchRetailerResponses;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponseResult {

    @SerializedName("result")
    @Expose
    private List<SearchResponseData> result = null;

    public List<SearchResponseData> getResult() {
        return result;
    }

    public void setResult(List<SearchResponseData> result) {
        this.result = result;
    }

}
