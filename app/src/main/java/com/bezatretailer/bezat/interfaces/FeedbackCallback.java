package com.bezatretailer.bezat.interfaces;


import com.bezatretailer.bezat.models.feedbackResponse.FeedbackResponse;

public interface FeedbackCallback {

    void onSuccess(FeedbackResponse responseResult);

    void onFailure(Throwable e);

}
