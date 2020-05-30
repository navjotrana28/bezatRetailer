package com.bezatretailernew.bezat.models.redeemUserOffer;

public class RedeemUserOfferRequest {
    String[] offer_code;
    String customer_code,phone_number;

    public String[] getOffer_code() {
        return offer_code;
    }

    public void setOffer_code(String[] offer_code) {
        this.offer_code = offer_code;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
