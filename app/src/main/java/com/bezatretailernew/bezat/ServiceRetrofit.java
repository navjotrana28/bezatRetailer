package com.bezatretailernew.bezat;

import com.bezatretailernew.bezat.api.contactusResponse.ContactUsResponse;
import com.bezatretailernew.bezat.models.LogoutResponse;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailernew.bezat.models.packageResponse.PackageResponse;
import com.bezatretailernew.bezat.models.searchRetailerResponses.SearchResponseResult;
import com.bezatretailernew.bezat.models.vip_lists.VipResult;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceRetrofit {

    @FormUrlEncoded
    @POST("user/sendEnquiry")
    Observable<ContactUsResponse> getContactSuccess(@Field("name") String name,
                                                    @Field("email") String email,
                                                    @Field("comments") String comments);


    @GET("category/list")
    Observable<SearchResponseResult> getSearchRetaierSuccess();

    @GET("user/get_vip?")
    Observable<VipResult> getVipLists(
            @Query("userid") String userId);


    @FormUrlEncoded
    @POST("user/feedback")
    Observable<FeedbackResponse> getFeedbackRequest(@Field("userId") String userId,
                                                    @Field("feedback") String feedback,
                                                    @Field("retailerId") String retailerId,
                                                    @Field("ratings") String ratings);

    @FormUrlEncoded
    @POST("staff/logout")
    Observable<LogoutResponse> getLogoutAPi(@Field("userID") String userId);

    @GET("staff/packages")
    Observable<PackageResponse> getPackages();


}

