package com.bezatretailernew.bezat;

import android.util.Log;

import com.bezatretailernew.bezat.api.contactusResponse.ContactUsRequest;
import com.bezatretailernew.bezat.api.contactusResponse.ContactUsResponse;
import com.bezatretailernew.bezat.interfaces.ContactUsSuccessResponse;
import com.bezatretailernew.bezat.interfaces.FeedbackCallback;
import com.bezatretailernew.bezat.interfaces.GetOfferCodeCallback;
import com.bezatretailernew.bezat.interfaces.RedeemUserOfferCallback;
import com.bezatretailernew.bezat.interfaces.SearchPackageInterface;
import com.bezatretailernew.bezat.interfaces.LogoutCallback;
import com.bezatretailernew.bezat.interfaces.SearchRetaierInterface;
import com.bezatretailernew.bezat.interfaces.VipListResponse;
import com.bezatretailernew.bezat.models.LogoutResponse;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackRequest;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailernew.bezat.models.getOfferCodes.GetOfferCodesResponse;
import com.bezatretailernew.bezat.models.packageResponse.PackageResponse;
import com.bezatretailernew.bezat.models.redeemUserOffer.RedeemUserOfferRequest;
import com.bezatretailernew.bezat.models.redeemUserOffer.RedeemUserOfferResponse;
import com.bezatretailernew.bezat.models.searchRetailerResponses.SearchResponseResult;
import com.bezatretailernew.bezat.models.vip_lists.VipResult;
import com.bezatretailernew.bezat.utils.URLS;

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

    public void vipListData(String userId, final VipListResponse vipListResponse) {
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

    public void logOutAPi(String userID, final LogoutCallback logoutCallback) {
        serviceRetrofit.getLogoutAPi(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LogoutResponse responseResult) {
                        logoutCallback.onSuccess(responseResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logoutCallback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getPackages(final SearchPackageInterface packageInterface){
        serviceRetrofit.getPackages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PackageResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PackageResponse packageResponseData) {
                        packageInterface.onSuccess(packageResponseData);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getOfferCodes(String retailerId,GetOfferCodeCallback getOfferCodeCallback){
        serviceRetrofit.getOfferCodes(retailerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetOfferCodesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetOfferCodesResponse getOfferCodeResponse) {
                        Log.d("---test---",getOfferCodeResponse.toString());
                        getOfferCodeCallback.onSuccess(getOfferCodeResponse);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void redeemUserOffer(RedeemUserOfferRequest request,RedeemUserOfferCallback redeemUserOfferCallback){
        serviceRetrofit.redeemUserOffer(request.getOffer_code(),request.getCustomer_code(),request.getCustomer_code())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RedeemUserOfferResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RedeemUserOfferResponse redeemOfferResponse) {
                        redeemUserOfferCallback.onSuccess(redeemOfferResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
