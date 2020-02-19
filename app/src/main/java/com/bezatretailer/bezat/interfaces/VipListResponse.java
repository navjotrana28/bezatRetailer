package com.bezatretailer.bezat.interfaces;

import com.bezatretailer.bezat.models.vip_lists.VipResult;

public interface VipListResponse {

    void onSuccess(VipResult response);

    void onFailure(Throwable e);
}
