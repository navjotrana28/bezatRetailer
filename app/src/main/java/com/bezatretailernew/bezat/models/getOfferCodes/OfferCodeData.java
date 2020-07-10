package com.bezatretailernew.bezat.models.getOfferCodes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferCodeData {

    @SerializedName("offer_coupon_code")
    @Expose
    private String offer_coupon_code;

    @SerializedName("offer_name")
    @Expose
    private String offer_name;

    @SerializedName("offer_name_ar")
    @Expose
    private String offer_name_ar;

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }

    public String getOffer_name_ar() {
        return offer_name_ar;
    }

    public void setOffer_name_ar(String offer_name_ar) {
        this.offer_name_ar = offer_name_ar;
    }

    public String getOffer_coupon_code() {
        return offer_coupon_code;
    }

    public void setOffer_coupon_code(String offer_coupon_code) {
        this.offer_coupon_code = offer_coupon_code;
    }
}
