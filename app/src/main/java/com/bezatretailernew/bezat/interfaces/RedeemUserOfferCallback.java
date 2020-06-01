package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.models.redeemUserOffer.RedeemUserOfferResponse;

public interface RedeemUserOfferCallback {

    void onSuccess(RedeemUserOfferResponse responseResult);

    void onFailure(Throwable e);

}
