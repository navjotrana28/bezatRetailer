package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.models.userPhoneResponse;

public interface UserPhoneInterface {
    void onSuccess(userPhoneResponse responseResult);

    void onFailure(Throwable e);
}
