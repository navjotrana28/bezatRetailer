package com.bezatretailer.bezat;

import com.bezatretailer.bezat.api.contactusResponse.ContactUsResponse;
import com.bezatretailer.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailer.bezat.models.searchRetailerResponses.SearchResponseResult;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceRetrofit {

//    @POST("user/sendEnquiry")
//    Observable<ContactUsResponse> getContactSuccess(@Body ContactUsRequest request);

    @FormUrlEncoded
    @POST("user/sendEnquiry")
    Observable<ContactUsResponse> getContactSuccess(@Field("name") String name,
                                                    @Field("email") String email,
                                                    @Field("comments") String comments);


    @GET("category/list")
    Observable<SearchResponseResult> getSearchRetaierSuccess();

    @FormUrlEncoded
    @POST("user/feedback")
    Observable<FeedbackResponse> getFeedbackRequest(@Field("userId") String userId,
                                                    @Field("feedback") String feedback,
                                                    @Field("retailerId") String retailerId,
                                                    @Field("ratings") String ratings);

    @FormUrlEncoded
    @POST("staff/logout")
    Call<Void> getLogoutAPi(@Field("userId") String userId);

}

