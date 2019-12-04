package com.bezatretailer.bezat;

import com.bezatretailer.bezat.api.contactusResponse.ContactUsRequest;
import com.bezatretailer.bezat.api.contactusResponse.ContactUsResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceRetrofit {

    @POST("user/sendEnquiry")
    Single<ContactUsResponse> getContactSuccess(@Body ContactUsRequest request);
}

