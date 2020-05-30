package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.models.getOfferCodes.GetOfferCodesResponse;

public interface GetOfferCodeCallback {

    void onSuccess(GetOfferCodesResponse responseResult);

    void onFailure(Throwable e);

}
