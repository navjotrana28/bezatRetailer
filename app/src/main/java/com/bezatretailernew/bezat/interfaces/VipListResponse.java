package com.bezatretailernew.bezat.interfaces;

import com.bezatretailernew.bezat.models.vip_lists.VipResult;

public interface VipListResponse {

    void onSuccess(VipResult response);

    void onFailure(Throwable e);
}
