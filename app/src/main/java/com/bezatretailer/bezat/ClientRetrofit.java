package com.bezatretailer.bezat;

import com.bezatretailer.bezat.api.contactusResponse.ContactUsRequest;
import com.bezatretailer.bezat.api.contactusResponse.ContactUsResponse;
import com.bezatretailer.bezat.interfaces.ContactUsSuccessResponse;
import com.bezatretailer.bezat.interfaces.FeedbackCallback;
import com.bezatretailer.bezat.interfaces.SearchRetaierInterface;
import com.bezatretailer.bezat.interfaces.VipListResponse;
import com.bezatretailer.bezat.models.feedbackResponse.FeedbackRequest;
import com.bezatretailer.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailer.bezat.models.searchRetailerResponses.SearchResponseResult;
import com.bezatretailer.bezat.models.vip_lists.VipResult;
import com.bezatretailer.bezat.utils.SharedPrefs;
import com.bezatretailer.bezat.utils.URLS;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRetrofit {


    private static Retrofit retrofit = null;
    private static ServiceRetrofit serviceRetrofit = null;

    public ClientRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URLS.BASE_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        serviceRetrofit = retrofit.create(ServiceRetrofit.class);
    }


    public void SendDataViaApi(ContactUsRequest request, final ContactUsSuccessResponse contactUsSuccessResponse) {
        serviceRetrofit.getContactSuccess(request.getName(), request.getEmail(), request.getComments())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContactUsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ContactUsResponse response) {
                        contactUsSuccessResponse.onSuccess(response);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void vipListData(String userId,final VipListResponse vipListResponse) {
        serviceRetrofit.getVipLists(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VipResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(VipResult response) {
                        vipListResponse.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        vipListResponse.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void SearchRetailerResult(final SearchRetaierInterface retaierInterface) {
        serviceRetrofit.getSearchRetaierSuccess()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResponseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchResponseResult responseResult) {
                        retaierInterface.onSuccess(responseResult);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void feedBackRequestApi(FeedbackRequest request, final FeedbackCallback callback) {
        serviceRetrofit.getFeedbackRequest(request.getUserId(), request.getFeedback(), request.getRetailerId(), request.getRatings())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedbackResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FeedbackResponse responseResult) {
                        callback.onSuccess(responseResult);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void logOutAPi(String userID) {
        serviceRetrofit.getLogoutAPi(userID);
    }

}
