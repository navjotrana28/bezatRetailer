package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.models.searchRetailerResponses.SearchResponseResult;

public interface SearchRetaierInterface {
    void onSuccess(SearchResponseResult responseResult);

    void onFailure(Throwable e);

}
