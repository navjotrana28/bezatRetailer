package com.bezatretailernew.bezat.interfaces;


import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackResponse;

public interface FeedbackCallback {

    void onSuccess(FeedbackResponse responseResult);

    void onFailure(Throwable e);

}
