package com.bezatretailer.bezat.interfaces;

import com.bezatretailer.bezat.api.contactusResponse.ContactUsResponse;

public interface ContactUsSuccessResponse {
    void onSuccess(ContactUsResponse response);

    void onFailure(Throwable e);
}