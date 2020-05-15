package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.api.contactusResponse.ContactUsResponse;

public interface ContactUsSuccessResponse {
    void onSuccess(ContactUsResponse response);

    void onFailure(Throwable e);
}