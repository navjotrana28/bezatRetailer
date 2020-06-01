package com.bezatretailernew.bezat.models.getOfferCodes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferCodeData {

    @SerializedName("offer_coupon_code")
    @Expose
    private String offer_coupon_code;

    public String getOffer_coupon_code() {
        return offer_coupon_code;
    }

    public void setOffer_coupon_code(String offer_coupon_code) {
        this.offer_coupon_code = offer_coupon_code;
    }
}
