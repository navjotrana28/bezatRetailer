package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.models.packageResponse.PackageResponse;

public interface SearchPackageInterface {

    void onSuccess(PackageResponse responseResult);

    void onFailure(Throwable e);

}
