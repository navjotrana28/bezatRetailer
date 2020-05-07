package com.bezatretailernew.bezat.interfaces;

public interface GetOtpInterface {
    void onOtpReceived(String otp);

    void onOtpTimeout();
}
