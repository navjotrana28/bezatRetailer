package com.bezatretailer.bezat;

import com.bezatretailer.bezat.api.contactusResponse.ContactUsRequest;
import com.bezatretailer.bezat.api.contactusResponse.ContactUsResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceRetrofit {

    @POST("user/sendEnquiry")
    Observable<ContactUsResponse> getContactSuccess(
            @Body ContactUsRequest request);
}

