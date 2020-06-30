package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.models.UserCodeResponse;
import com.bezatretailernew.bezat.models.userPhoneResponse;

public interface UserCodeInterface {
    void onSuccess(UserCodeResponse responseResult);

    void onFailure(Throwable e);
}
