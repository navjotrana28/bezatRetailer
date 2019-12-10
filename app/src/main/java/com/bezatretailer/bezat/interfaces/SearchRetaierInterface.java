package com.bezatretailer.bezat.interfaces;

import com.bezatretailer.bezat.models.searchRetailerResponses.SearchResponseResult;

public interface SearchRetaierInterface {
    void onSuccess(SearchResponseResult responseResult);

    void onFailure(Throwable e);

}
