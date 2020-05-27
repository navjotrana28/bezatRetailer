package com.bezatretailernew.bezat.interfaces;


import com.bezatretailernew.bezat.models.LogoutResponse;

public interface LogoutCallback {

    void onSuccess(LogoutResponse responseResult);

    void onFailure(Throwable e);

}
